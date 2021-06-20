package app;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import data.Evento;
import data.EventoConOpciones;

public class Main
{
	public static void main(String[] args) 
	{
		Evento evento1 = new Evento("Evento 1", "Descripcion Evento 1", true);
		Evento evento2 = new Evento("Evento 2", "Descripcion Evento 2", true);
		Evento evento3 = new Evento("Evento 3", "Descripcion Evento 3", true);
		
		ArrayList<String> arrayOpc = new ArrayList<String>();
		arrayOpc.add("Opcion 1");
		arrayOpc.add("Opcion 2");
		arrayOpc.add("Opcion 3");
		
		ArrayList<Integer> arrayDatos = new ArrayList<Integer>();
		arrayDatos.add(1000);
		arrayDatos.add(-200);
		arrayDatos.add(1500);
		
		EventoConOpciones eventoConOpc1 = new EventoConOpciones("Evento con Opciones 1", "Descripcion Evento 1", true, arrayOpc, arrayDatos);
		EventoConOpciones eventoConOpc2 = new EventoConOpciones("Evento con Opciones 2", "Descripcion Evento 2", true, arrayOpc, arrayDatos);
		EventoConOpciones eventoConOpc3 = new EventoConOpciones("Evento con Opciones 3", "Descripcion Evento 3", true, arrayOpc, arrayDatos);
		
		ArrayList<Evento> arrayEventosSalida = new ArrayList<Evento>();
		
		arrayEventosSalida.add(evento1);
		arrayEventosSalida.add(evento2);
		arrayEventosSalida.add(evento3);
		
		arrayEventosSalida.add(eventoConOpc1);
		arrayEventosSalida.add(eventoConOpc2);
		arrayEventosSalida.add(eventoConOpc3);
		
		grabarEvento("eventos.dat", arrayEventosSalida);
		
		printArrayEventos(leerEventos("eventos.dat"));

	}
	
	public static void printArrayEventos(ArrayList<Evento> arrayEventosEntrada)
	{
		System.out.println("<<< Eventos en el Archivo >>> \n");
		
		for(Evento evento : arrayEventosEntrada)
		{
			System.out.println("---------------------------------------------------------------");
			System.out.println(evento.getNombre());
			System.out.println(evento.getDescripcion());
			System.out.println("Activo: " + evento.getActivo() + "\n");
			
			if (evento instanceof EventoConOpciones)
			{
				EventoConOpciones eventoOpc = (EventoConOpciones) evento;
				
				System.out.println("<< Opciones >>:");
				
				for(int i=0; i < eventoOpc.getArrayOpciones().size(); i++)
				{
					System.out.println(eventoOpc.getArrayOpciones().get(i));
					System.out.println("Resultado opc "+ i + ":" + eventoOpc.getArrayDatos().get(i));
				}
			}
			System.out.println("---------------------------------------------------------------");
		}
	}
	
	public static void grabarEvento(String nombreArchivo, ArrayList<Evento> arrayEventosSalida)
	{
		try 
		{
			OutputStream fos = new FileOutputStream(nombreArchivo);
			
			try (ObjectOutputStream oos = new ObjectOutputStream(fos))
			{
				oos.writeInt(arrayEventosSalida.size());
				
				for (Evento evento : arrayEventosSalida)
				{
					oos.writeObject(evento);
				}
			}
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		} 
		catch (FileNotFoundException e1) 
		{
			System.out.println("No se encontro en archivo indicado");
		}
	}
	
	public static ArrayList<Evento> leerEventos(String nombreArchivo)
	{
		ArrayList<Evento> arrayEventos = new ArrayList<Evento>();

		try 
		{
			FileInputStream fis = new FileInputStream(nombreArchivo);
			
			try (ObjectInputStream ois = new ObjectInputStream(fis))
			{
				Integer eventCount = ois.readInt();
				
				for (int i=0; i < eventCount; i++)
				{
					arrayEventos.add((Evento) ois.readObject());
				}
			}
			catch (EOFException e)
			{
				System.out.println("<<< Fin del Archivo >>>");
			}
			catch (IOException e)
			{
				System.out.println("IOEXCEPTION:" + e.getMessage());
			}
			catch (ClassNotFoundException e)
			{
				System.out.println("CLASSNOTFOUND:" + e.getMessage());
			}
		} 
		catch (FileNotFoundException e1) 
		{
			System.out.println("No se encontro el archivo");
		}
		
		return arrayEventos;
	}
}
