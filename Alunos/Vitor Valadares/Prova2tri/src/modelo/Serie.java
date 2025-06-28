package modelo;

import java.util.List;

public class Serie {
	private String nome;
	private String idioma;
	private List<String> generos;
	private double nota;
	private String status;
	private String estreia;
	private String fim;
	private String emissora;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getIdioma() {
		return idioma;
	}

	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}

	public List<String> getGeneros() {
		return generos;
	}

	public void setGeneros(List<String> generos) {
		this.generos = generos;
	}

	public double getNota() {
		return nota;
	}

	public void setNota(double nota) {
		this.nota = nota;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getEstreia() {
		return estreia;
	}

	public void setEstreia(String estreia) {
		this.estreia = estreia;
	}

	public String getFim() {
		return fim;
	}

	public void setFim(String fim) {
		this.fim = fim;
	}

	public String getEmissora() {
		return emissora;
	}

	public void setEmissora(String emissora) {
		this.emissora = emissora;
	}

	@Override
	public String toString() {
		return nome + " | " + idioma + " | " + generos + " | Nota: " + nota + " | Status: " + status + " | Estreia: "
				+ estreia + " | Fim: " + fim + " | Emissora: " + emissora;
	}
}
