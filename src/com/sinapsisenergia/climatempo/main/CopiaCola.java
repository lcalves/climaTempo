package com.sinapsisenergia.climatempo.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class CopiaCola {

	public static void main(String[] args) throws IOException {
		
		
		File diretorio = new File("G:\\leo\\workspace\\arquivos.csv");
		
		List<String> arquivoList = new ArrayList<String>();
		
		FileReader inputStream = new FileReader(diretorio);
		
		BufferedReader arquivoEntrada = new BufferedReader(inputStream);
		
		String linha;
		
		while((linha = arquivoEntrada.readLine()) != null){
			arquivoList.add(linha);
			
		}
		
		File arquivos[];
		
		File diretorio2 = new File("G:\\leo\\Clima");
		arquivos = diretorio2.listFiles();
		File dest;
		for(File arquivo : arquivos){
			
			if(!arquivoList.contains(arquivo.getName())){
			
				dest = new File("G:\\leo\\Clima\\raios\\"+arquivo.getName());
				mover(arquivo, dest);
			}
		}
			
			arquivoEntrada.close();
			inputStream.close();
			
	}

	private static void mover(File arquivo, File dest) throws IOException {
		 InputStream is = null;
		    OutputStream os = null;
		    try {
		        is = new FileInputStream(arquivo);
		        os = new FileOutputStream(dest);
		        byte[] buffer = new byte[1024];
		        int length;
		        while ((length = is.read(buffer)) > 0) {
		            os.write(buffer, 0, length);
		        }
		    } finally {
		        is.close();
		        os.close();
		    }
		
	}



}
