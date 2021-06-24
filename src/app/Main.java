package app;


import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Scanner;
import data.*;
import org.json.*;


public class Main
{
	public static Scanner scan = new Scanner(System.in);
	
	public static void main(String[] args) 
	{
		//ArrayList<EmpresaEnemiga> empresas = new ArrayList<EmpresaEnemiga>();
		ArrayList<Evento> eventos = leerEventos("eventos.dat");
		
		printArrayEventos(leerEventos("eventos.dat"));
		
		//crearEmpresasEnemigas(empresas);
		//empresasToJson(empresas);
		crearEventos(eventos);
		grabarEvento("eventos.dat", eventos);
		
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
			else
			{
				System.out.println("Valor del evento: "+evento.getValor());
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
					oos.writeObject(arrayEventosSalida);	
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
	
	@SuppressWarnings("unchecked")
	public static ArrayList<Evento> leerEventos(String nomArchiEventos) // devuelve un array list con todos los eventos cargados en el archivo.
	{
		ArrayList<Evento> arrayEventos = new ArrayList<Evento>();

		try 
		{
			FileInputStream fis = new FileInputStream(nomArchiEventos);
			
			try
			{
				ObjectInputStream ois = new ObjectInputStream(fis);
				arrayEventos = (ArrayList<Evento>) ois.readObject();
				
				ois.close();
				fis.close();
			
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
	
	public static void crearEmpresasEnemigas(ArrayList<EmpresaEnemiga> empresas) //crear 10 empresas solo con nombre y CEO
	{
		int i = 0;
		boolean continuar = true;
		String nombre = new String();
		String ceo = new String();
		int aux;
		while(i<10 && continuar)
		{
			System.out.println("\nNombre empresa n°"+(i+1));
			nombre = scan.nextLine();
			System.out.println("\nCEO empresa n°"+(i+1));
			ceo = scan.nextLine();
			
			
			EmpresaEnemiga empresa = new EmpresaEnemiga(nombre, ceo);
			
			empresas.add(empresa);
			
			System.out.println("Desea agregar otra empresa? 1 para SI; 0 para NO");
			aux = scan.nextInt();
			scan.nextLine();
			if(aux == 1)
			{
				continuar = true;
				i++;
			}
			else
			{
				continuar = false;
			}
		}
	}
	
	public static void crearEventos(ArrayList<Evento> eventos)
	{
		boolean continuar = true;
		
		do
		{
			String nombre = new String();
			String desc = new String();
			//ArrayList<String> arrayOpc = new ArrayList<String>();
			//ArrayList<Integer> arrayDatos = new ArrayList<Integer>();
			int aux = 0;
			boolean opc = false;
			double valor = 0;
			scan = new Scanner(System.in);
			System.out.println("\nNombre del evento:");
			nombre = scan.nextLine();
			System.out.println("\nDescripcion del evento: ");
			desc = scan.nextLine();
			
			//System.out.println("\nEs un evento con opciones?\n");
			
			//aux = scan.nextInt();
			
			if(aux == 1)
			{
				opc = true;
			}
			else
			{
				opc = false;
			}
			
			if(opc)
			{
				/*int i = 0;
				
				while(continuar)
				{
					scan = new Scanner(System.in);
					System.out.println("\nOpción n°"+i);
					arrayOpc.add(scan.nextLine());
					System.out.println("\nDato opción n°"+i);
					arrayDatos.add(scan.nextInt());
					System.out.println("\n\nOtra opción? 1 para SI ; 0 para NO");
					aux = scan.nextInt();
					
					if(aux == 1)
					{
						continuar = true;
						i++;
					}
					else
					{
						continuar = false;
					}
					
				}
				
				
				Evento evento = new EventoConOpciones(nombre, desc, false, arrayOpc, arrayDatos);
				
				eventos.add(evento);*/
			}
			else
			{
				scan = new Scanner(System.in);
				System.out.println("Ingrese un valor para el evento");
				valor = scan.nextDouble();
				
				System.out.println("Ingrese 1 si es un evento activo. De lo contrario ingrese 0.");
				aux = scan.nextInt();
				
				if(aux == 1)
				{
					opc = true;
				}
				else
				{
					opc = false;
				}
				
				Evento evento = new Evento(nombre, desc, opc, valor);
				eventos.add(evento);
			}
			
			System.out.println("Desea agregar otro evento? 1 para SI; 0 para NO");
			aux = scan.nextInt();
			
			if(aux == 1)
			{
				continuar = true;
			}
			else
			{
				continuar = false;
			}
			
		}while(continuar);
	}
	
	public static void empresasToJson(ArrayList<EmpresaEnemiga> arrayEmpresas)
	{
		
		int i = 0;
		
		JSONArray empresas = new JSONArray();
		try
		{
			FileWriter file = new FileWriter("EmpresasEnemigas.json");
			
			for(i=0;i<arrayEmpresas.size();i++)
			{
				JSONObject empresa = new JSONObject();
				empresa.put("Nombre de la empresa", arrayEmpresas.get(i).getNombre().toString());
				empresa.put("CEO", arrayEmpresas.get(i).getCEO().toString());
				
				empresas.put(empresa);
			}
			
			file.write(empresas.toString());
			file.close();
			System.out.println("Escritura del archivo completada. Archivo cerrado.");
			
		}catch(Exception e)
		{
			System.out.println("Error en la escritura del archivo");
		}
		
	}
	
	
	
}













