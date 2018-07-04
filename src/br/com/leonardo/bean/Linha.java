package br.com.leonardo.bean;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Linha {
	
	private double latitude;
	private double longitude;
	private BigDecimal medicao;
	private LocalDate dataAmostragem;
	
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public BigDecimal getMedicao() {
		return medicao;
	}
	public void setMedicao(BigDecimal medicao) {
		this.medicao = medicao;
	}
	public LocalDate getDataAmostragem() {
		return dataAmostragem;
	}
	public void setDataAmostragem(LocalDate dataAmostragem) {
		this.dataAmostragem = dataAmostragem;
	}
	
}
