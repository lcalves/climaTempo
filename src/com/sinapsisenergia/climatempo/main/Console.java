package com.sinapsisenergia.climatempo.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import br.com.leonardo.bean.Linha;
import br.com.leonardo.dao.ConnectionManager;
import br.com.leonardo.dao.LinhaDAO;

public class Console {
	
	//
	//ATRIBUTOS
	//
	
	private static final DateTimeFormatter SDF2 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	public static Properties p;
	
	public static void main(String[] args) throws IOException, SQLException {
		
		p = loadProperties();
		Connection conn = ConnectionManager.getInstance().getConnection();
		
		File arquivos[];
		
		File diretorio = new File(p.getProperty("CAMINHO_PASTA"));
		int qtLinhas = 0;
		int qtArquivoTot;
		int qtArquivosProc =0;
		
		arquivos = diretorio.listFiles();
		
		//atenca
		FileReader inputStream;
		BufferedReader arquivoEntrada;
		
		String linha;
		String [] conteudo; 
		LocalDate data;
		
		String dataFormatada;
		String dia;
		String mes;
		String ano;
		String pasta = p.getProperty("PASTA");
		
		long tempInicial;
		long tempFinal;
		long tempTotalInicial = System.currentTimeMillis();
		
		LinhaDAO dao = new LinhaDAO();
		String tabela;
		String amostragem = "";
		Linha linhaBean = new Linha();
		qtArquivoTot = arquivos.length;
		List<Linha> list;
		int i = 0;
		for(File arquivo : arquivos){
			
			
			if(i++ == 30){
				System.out.println("Fechando Conexão");
				i=0;
				conn.close();
				conn = ConnectionManager.getInstance().getConnection();
				System.out.println("Conectado...");
			}
				
				tempInicial = System.currentTimeMillis();
		
				inputStream = new FileReader(arquivo.getPath());
				arquivoEntrada = new BufferedReader(inputStream);
				
				dataFormatada = arquivo.getName().substring(arquivo.getName().indexOf("_")+1, arquivo.getName().indexOf("."));
				tabela = arquivo.getName().substring(0, arquivo.getName().indexOf("_"));
				dia = dataFormatada.substring(6);
				mes = dataFormatada.substring(4, 6);
				ano = dataFormatada.substring(0, 4);
				list  = new ArrayList<Linha>();
				data = LocalDate.parse(dia + "/" + mes + "/" + ano, SDF2); 
				
				while((linha = arquivoEntrada.readLine()) != null){
					
					conteudo = linha.split(",");

					if(!conteudo[0].equals("x")){
						linhaBean = new Linha();
						
						linhaBean.setLatitude(Double.parseDouble(conteudo[1]));
						linhaBean.setLongitude(Double.parseDouble(conteudo[0]));
						linhaBean.setDataAmostragem(data);
						linhaBean.setMedicao(new BigDecimal(conteudo[2]));
						
						list.add(linhaBean);
						
					}
					
				}
				
				System.out.println(tabela);
				switch (tabela+pasta) {
				case "densidade_10km":
					tabela = "TB_RAIO_DENSIDADE_10KM";
					amostragem = "DENSIDADE";
					break;
				case "picocorrente_10km":
					tabela = "TB_RAIO_PICO_COR_10KM";
					amostragem = "PICO_CORRENTE";
					break;
				case "picocorrente_50km":
					tabela = "TB_RAIO_PICO_COR_50KM";
					amostragem = "PICO_CORRENTE";
					break;
				case "densidade_50km":
					tabela = "TB_RAIO_DENSIDADE_50KM";
					amostragem = "DENSIDADE";
					break;
				case "direcaomed_":
					tabela = "TB_VENTO_DIRECAO_MED";
					amostragem = "DIRECAO";
					break;
				case "ventomax_":
					tabela = "TB_VENTO_MAX";
					amostragem = "VELOCIDADE";
					break;
				case "ventomed_":
					tabela = "TB_VENTO_MED";
					amostragem = "VELOCIDADE";
					break;
				case "chuva_":
					tabela = "TB_PRECIPITACAO";
					amostragem = "NIVEL_PRECIPITACAO_CHUVA";
					break;
					

				}
			
				dao.incluir(list, tabela, amostragem, conn);
			
				tempFinal = System.currentTimeMillis();
				qtLinhas += list.size();
				qtArquivosProc++;
				System.out.println( list.size() +" Linhas inseridas em: " + (tempFinal - tempInicial) + " ms. total linhas inseridas " + qtLinhas);
				System.out.println("Arquivos processados: "+qtArquivosProc +" / " + qtArquivoTot);
				arquivoEntrada.close();
				inputStream.close();
				System.gc();
			}
		
		conn.close();
		
		System.out.println("Tempo total de execução: " + (System.currentTimeMillis() - tempTotalInicial)/1000 + " seg. (" + arquivos.length + ") Arquivos inseridos");
			
	}

//	public  static LocalDate convertData(String data) {
//		try {
//			
//			f2.setTime(SDF.parse(data));
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//		return f2;
//	}
	
	public static Properties loadProperties() throws IOException, IllegalArgumentException {
		Properties prop = new Properties();
		File f = new File("G:/leo/workspace/climaTempoRefactor/climaTempoRefactor/properties/dados.properties");
		InputStream input = null;
		input = new FileInputStream(f);
		prop.load(input);
		return prop;

	}
	

}
