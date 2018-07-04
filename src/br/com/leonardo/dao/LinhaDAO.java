package br.com.leonardo.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.leonardo.bean.Linha;

public class LinhaDAO {

	private PreparedStatement stmt;
	
	
	
	public void incluir(List<Linha> linhas, String tabela, String amostragem, Connection conn) throws SQLException {

		try {

			for (Linha linha : linhas) {

				stmt = conn.prepareStatement("insert into " + tabela + " (LATITUDE,LONGITUDE,"
						+ amostragem + ",DATA_OCORRENCIA) values " + "(?,?,?,?)");
				// System.out.println("GRAVANDO LATITUDE " +
				// linha.getLatitude());
				stmt.setDouble(1, linha.getLatitude());

				// System.out.println("GRAVANDO Longitude " +
				// linha.getLongitude());
				stmt.setDouble(2, linha.getLongitude());

				stmt.setBigDecimal(3, linha.getMedicao());

				stmt.setDate(4, java.sql.Date.valueOf(linha.getDataAmostragem()));

				stmt.execute();
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException("Erro ao conectar ou manipular o banco de dados!", e);
		} 
	}

	public ArrayList<Linha> listar(Calendar dtInicial, Calendar dtFinal, String tabela, String campoTabela)
			throws SQLException, IllegalArgumentException, IOException {

		// Lista a ser retornada

		ArrayList<Linha> linhaList = new ArrayList<Linha>();

		Connection conn = null;// conexao com o SGBDR

		try {

			// Obtem a conexao com o SGBDR
			conn = ConnectionManager.getInstance().getConnection();

			PreparedStatement stmtSelect = conn.prepareStatement(
					"SELECT * from " + tabela + " where data_ocorrencia in (" + dtInicial + " and " + dtFinal);

			// executa a SQL

			ResultSet rs = stmtSelect.executeQuery();

			while (rs.next()) {

//				Linha linha = criarLinha(rs, campoTabela);

//				linhaList.add(linha);

			}

		} catch (SQLException e) {

			e.printStackTrace();

			throw new SQLException("Erro ao conectar ou manipular o banco de dados!", e);

		} finally {

			if (conn != null) {

				try {
					conn.close();

				} catch (SQLException e) {

					e.printStackTrace();

					throw new SQLException("Erro ao fechar a conexao com o banco de dados!", e);

				}

			}

		}

		return linhaList;

	}

//	private Linha criarLinha(ResultSet rs, String campoTabela) throws SQLException {
//		// cria um objeto funcionario (que contem os dados deste registro)
//
//		Linha linha = new Linha();
//
//		// pega o valor da coluna e coloca na propriedade do objeto
//
//		linha.setLatitude(rs.getDouble("latitude"));
//		linha.setLongitude(rs.getDouble("longitude"));
//		linha.setMedicao(rs.getBigDecimal(campoTabela));
//
//		Calendar data = Calendar.getInstance();
//		data.setTime(rs.getDate("latitude"));
//		linha.setDataAmostragem(data);
//
//		return linha;
//	}

}
