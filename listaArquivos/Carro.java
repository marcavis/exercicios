package listaArquivos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class Carro {
	private String marca, modelo, placa;
	private int ano;
	
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	public String getPlaca() {
		return placa;
	}
	public void setPlaca(String placa) {
		this.placa = placa;
	}
	public int getAno() {
		return ano;
	}
	public void setAno(int ano) {
		this.ano = ano;
	}
	
	public String[] toArray() {
		return new String[]{getMarca(), getModelo(), getPlaca(), getAno()+""};
	}
	
	public boolean gravaTxt() {
		try {
			FileWriter fw = new FileWriter(new File("carros.txt"), true);
			//true seta o modo append, em vez de criar novo arquivo
			BufferedWriter bw = new BufferedWriter(fw);
			String linha = getMarca()+","+getModelo()+","+getPlaca()+","+getAno()+"\n";
			//faz a inserção
			bw.append(linha);
			bw.close();
			fw.close();
			return true;
		}
		catch(Exception e) {
			return false;
		}
	}
	
	public static ArrayList<Carro> lerTxt() {
		ArrayList<Carro> lista = new ArrayList<Carro>();
		try {
			FileReader fr = new FileReader(new File("carros.txt"));
			BufferedReader br = new BufferedReader(fr);
			String linha;
			while((linha=br.readLine())!=null) {
				String[] v = linha.split(",");
				Carro c = new Carro();
				c.setMarca(v[0]);
				c.setModelo(v[1]);
				c.setPlaca(v[2]);
				c.setAno(Integer.parseInt(v[3]));
				lista.add(c);
			}
			br.close();
			fr.close();
			
		} catch(Exception e) {
			//e.printStackTrace();
		}
		return lista;
	}
}
