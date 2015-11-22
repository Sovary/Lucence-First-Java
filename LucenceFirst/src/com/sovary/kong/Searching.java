package com.sovary.kong;

import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.FSDirectory;

public class Searching {

	private IndexReader reader;
	private IndexSearcher searcher;
	private Analyzer analyzer;
	private QueryParser parser;
	private String indexPath="D:\\myindex";
	public Searching() {
		// TODO Auto-generated constructor stub
	}
	public void doSearch(String searchquery){
		try{
			
			reader=DirectoryReader.open(FSDirectory.open(Paths.get(indexPath)));//Read from Index file
			searcher=new IndexSearcher(reader);//Doing searching
			analyzer=new StandardAnalyzer();
			parser=new QueryParser("text",analyzer);//To search in index file via field text.
			//parser=new MultiFieldQueryParser(new String[]{"text","date"},analyzer); //For search multi field
			Query query;
			
			query=parser.parse(searchquery);
			TopScoreDocCollector collector=TopScoreDocCollector.create(10); //Start retrieve data temperately 10 rows
			searcher.search(query, collector);//manipulate data and pass to collector
			
			System.out.println("Records found:"+collector.getTotalHits());
			
			ScoreDoc[] hits=collector.topDocs().scoreDocs;
			
			for(int i=0;i<hits.length;i++){
				Document doc=searcher.doc(hits[i].doc);
				System.out.println(i+1+" |Text: "+doc.get("text")+" |Date "+doc.get("date"));
			}
			
			reader.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
