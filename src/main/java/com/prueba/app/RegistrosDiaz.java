package com.prueba.app;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import com.prueba.model.Reserva;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class RegistrosDiaz extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnListar;
	private JTextArea txtS;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegistrosDiaz frame = new RegistrosDiaz();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public RegistrosDiaz() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 356, 489);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 46, 323, 393);
		contentPane.add(scrollPane);

		txtS = new JTextArea();
		scrollPane.setViewportView(txtS);

		listar();
	}

	void listar() {
		// conexión
		EntityManagerFactory conexion = Persistence.createEntityManagerFactory("LP2_T1_DIAZ");
		// manejador de entidades
		EntityManager manager = conexion.createEntityManager();
		// proceso obtener un objeto usuario

		String jpql = "select r from Reserva r";
		// String jpq2 = "select u.nom_usuar,u.ape_usua form Usuario u";

		List<Reserva> lstReservas = manager.createQuery(jpql, Reserva.class).getResultList();
		imprimir("Listado de Reservas");
		imprimir("***************************");
		double count = 0;
		for (Reserva r : lstReservas) {
			imprimir("NUM RESERVA\t\t : " + r.getNum_reserva());
			imprimir("NOMBRE\t\t : " + r.getNombre_alumno());
			imprimir("FECHA\t\t : " + r.getFecha_inscripcion());
			imprimir("GRADO\t\t : " + r.getObjGrado().getDescripcion());
			imprimir("MODALIDAD\t\t : " + r.getObjModalidad().getDescripcion());
			imprimir("PRECIO GRADO\t: " + r.getObjGrado().getPrecio());
			count += r.getObjGrado().getPrecio();

			imprimir("***********************");
		}
		imprimir("TOTAL PRECIOS\t : " + count);
		manager.close();
	}

	void imprimir(String s) {
		txtS.append(s + "\n");
	}

}
