package com.bdqn.lucene;

import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Fragmenter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.SimpleSpanFragmenter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.util.StringUtils;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.bdqn.entity.Blog;
import com.bdqn.utils.DateUtil;

/**
 * 博文索引
 * @author ASUS
 *
 */
public class BlogIndex {

	// 索引库存放位置
	private Directory dir;

	/**
	 * 获取indexWriter 实例
	 * @return
	 * @throws IOException 
	 */
	private IndexWriter getIndexWriter() throws IOException {
		dir = FSDirectory.open(Paths.get("D:\\LuceneStore\\blogmanager")); // 文件夹删除需谨慎（删除后之前所创的索引将会被清空）

		// 创建索引器
		IndexWriter indexWriter = new IndexWriter(dir, new IndexWriterConfig(new IKAnalyzer()));

		return indexWriter;
	}

	/**
	 * 添加博客索引
	 * @param blog
	 * @throws IOException 
	 */
	public void addIndex(Blog blog) throws IOException {
		IndexWriter indexWriter = getIndexWriter();

		// 创建文档对象
		Document document = new Document();

		// 参数1：域名 参数2：域值 参数3：是否存储
		document.add(new StringField("id", String.valueOf(blog.getId()), Store.YES));
		document.add(new TextField("title", blog.getTitle(), Store.YES));
		document.add(new StringField("releaseDate", DateUtil.formatDate(blog.getReleaseDate(), "yyyy-MM-dd"), Store.YES));
		// 内容是不带有标签的纯文本内容
		document.add(new TextField("content", blog.getContentNoTag(), Store.YES));

		// 写入文档
		indexWriter.addDocument(document);

		indexWriter.close();
	}

	/**
	 * 查询博客信息
	 * @param keyWord
	 * @return
	 * @throws IOException 
	 * @throws ParseException 
	 * @throws InvalidTokenOffsetsException 
	 */
	public List<Blog> searchBlog(String keyWord) throws IOException, ParseException, InvalidTokenOffsetsException{
		List<Blog> list = new ArrayList<Blog>();
		// 读取索引文件
		dir = FSDirectory.open(Paths.get("D:\\LuceneStore\\blogmanager"));
		// 创建IndexReader 对象
		IndexReader indexReader = DirectoryReader.open(dir);
		// 创建IndexSearch 对象
		IndexSearcher indexSearcher = new IndexSearcher(indexReader);
		// 创建IK分词器
		IKAnalyzer ikAnalyzer = new IKAnalyzer();
		// 创建QueryParser 对象，指定查询范围即使用哪种分词器
		QueryParser parserTitle = new QueryParser("title", ikAnalyzer);
		// 根据关键字进行分词，得到Query 对象
		Query queryTitle = parserTitle.parse(keyWord); // 解析标题中包含关键字的内容

		QueryParser parserContent = new QueryParser("content",ikAnalyzer);
		Query queryContent = parserContent.parse(keyWord); // 解析内容中包含关键字的内容

		// 创建条件查询对象，封装 query
		// Builder builder = new Builder(); 同下
		BooleanQuery.Builder builder = new BooleanQuery.Builder();
		builder.add(queryTitle, Occur.SHOULD);
		builder.add(queryContent,Occur.SHOULD);

		// 获取文档列表，并指定查询前几条数据
		TopDocs topDocs = indexSearcher.search(builder.build(), 100);

		// 得到查询内容最高分结果
		QueryScorer queryScorer = new QueryScorer(queryTitle);
		// 创建SimpleSpanFragmenter模板对象
		Fragmenter fragmenter=new SimpleSpanFragmenter(queryScorer);
		//创建SimpleHTMLFormatter对象
		SimpleHTMLFormatter simpleHTMLFormatter=new SimpleHTMLFormatter("<b><font color='red'>", "</font></b>");
		//高亮显示
		Highlighter highlighter=new Highlighter(simpleHTMLFormatter, queryScorer);
		//将高亮显示的内容放到模板中
		highlighter.setTextFragmenter(fragmenter);

		// 循环遍历文档列表
		for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
			// 获取每一个文档对象
			Document document = indexSearcher.doc(scoreDoc.doc);
			
			//创建博客对象
			Blog blog = new Blog();
			blog.setId(Integer.parseInt(document.get("id")));//取出文档id
			blog.setReleaseDateStr(document.get("releaseDate"));//取出发布日期
			String title = document.get("title");
			String content = document.get("content");
			
			// 如果标题内容不为空
			if (title != null) {
				// 创建一个TokenStream 
				TokenStream tokenStream=ikAnalyzer.tokenStream("title", new StringReader(title));
				//得到最佳匹配的标题内容
				String hTitle=highlighter.getBestFragment(tokenStream, title);
				//如果从lucene取出的标题为空，则显示普通效果
				if(StringUtils.isEmpty(hTitle)){
					blog.setTitle(title);
				}else{
					//高亮显示
					blog.setTitle(hTitle);
				}
			}
			
			//内容不为空
			if(content!=null){
				//对内容进行分词
				TokenStream tokenStream=ikAnalyzer.tokenStream("content", new StringReader(content));
				//获取最佳匹配的内容
				String hContent=highlighter.getBestFragment(tokenStream, content);
				//如果最佳匹配的数据为空，则按默认的样式显示（在页面中显示黑体字）
				if(StringUtils.isEmpty(hContent)){
					//如果博客内容长度小于200，则直接显示
					if(content.length()<=200){
						blog.setContent(content);						
					}else{
						////如果博客内容长度大于200，则截取前200个文字
						blog.setContent(content.substring(0, 200));	
					}
				}else{
					//高亮显示
					blog.setContent(hContent);
				}
			}
			
			//将博客信息添加到集合中
			list.add(blog);
		}
		return list;
	}
	
	/**
	 * 删除索引
	 * @param id
	 * @throws IOException 
	 */
	public void deleteIndex(String id) throws IOException {
		IndexWriter indexWriter = getIndexWriter();
		
		indexWriter.deleteDocuments(new Term("id", id));
		
		// 强制删除，避免删除不够彻底
		indexWriter.forceMergeDeletes();
		
		// 提交
		indexWriter.commit();
		
		//  释放资源
		indexWriter.close();
	}
	
	/**
	 * 更新博客索引
	 * @param blog
	 * @throws IOException 
	 */
	public void updateIndex(Blog blog) throws IOException {
		IndexWriter indexWriter = getIndexWriter();

		// 创建文档对象
		Document document = new Document();

		// 参数1：域名 参数2：域值 参数3：是否存储
		document.add(new StringField("id", String.valueOf(blog.getId()), Store.YES));
		document.add(new TextField("title", blog.getTitle(), Store.YES));
		document.add(new StringField("releaseDate", DateUtil.formatDate(blog.getReleaseDate(), "yyyy-MM-dd"), Store.YES));
		// 内容是不带有标签的纯文本内容
		document.add(new TextField("content", blog.getContentNoTag(), Store.YES));

		// 更新文档
		indexWriter.updateDocument(new Term("id",String.valueOf(blog.getId())), document);

		indexWriter.close();
	}
}
