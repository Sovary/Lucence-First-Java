package com.sovary.kong;

import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class Indexing {

	private Analyzer analyzer;
    private String indexPath="D:\\myindex";
	private boolean check=true;
	
	public Indexing() {
		// TODO Auto-generated constructor stub
	}
	public boolean createIndex(){
		try{
			
			analyzer=new StandardAnalyzer(); //Check on Keyword or sentence by analyzer standard 
			Directory dir=FSDirectory.open(Paths.get(indexPath));// create directory to store index file
			IndexWriterConfig config=new IndexWriterConfig(analyzer);
			
			if(check){
				config.setOpenMode(OpenMode.CREATE);  //Create index first time
				check=false;
			}else{
				config.setOpenMode(OpenMode.CREATE_OR_APPEND);
			}
			IndexWriter writer =new IndexWriter(dir, config);//Start write index
			
			addIndex(writer, "Cambodia is my country","20151121"); //Date format YYYYMMDD
			addIndex(writer, "In Asia also has Cambodia","20151122");
			addIndex(writer, "Hello Lucence technology","20151115");
			addIndex(writer, "Technology is updated every day","20151115");
			addIndex(writer, "I am Cambodian","20151105");
			writer.close();
			System.out.println("Create index done!");
			return true;
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return false;
	}
	public void addIndex(IndexWriter writer, String text,String date){
		
		try{
			Document doc=new Document();
			doc.add(new TextField("text", text,Field.Store.YES));//TextField use for token data sentences to word, and store field as text
			doc.add(new StringField("date", date,Field.Store.YES));//StringField use not for token, and store field as date
			writer.addDocument(doc);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}

}
