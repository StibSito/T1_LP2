package com.prueba.app;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.prueba.model.Grado;
import com.prueba.model.Modalidad;
import com.prueba.model.Reserva;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class reservaDiaz extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtDni;
	private JTextField txtNombre;
	private JTextField txtEmail;
	private JTextField txtTelefono;
	private JComboBox<String> cboGrados;
	private JComboBox<String> cboModalidades;
	private JButton btnRegistrar;
	private JLabel lblNewLabel_4;
	private JTextField txtFecha;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					reservaDiaz frame = new reservaDiaz();
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
	public reservaDiaz() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 322, 371);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("DNI :");
		lblNewLabel.setBounds(10, 11, 46, 14);
		contentPane.add(lblNewLabel);

		txtDni = new JTextField();
		txtDni.setBounds(83, 8, 167, 20);
		contentPane.add(txtDni);
		txtDni.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("NOMBRE :");
		lblNewLabel_1.setBounds(10, 36, 65, 14);
		contentPane.add(lblNewLabel_1);

		txtNombre = new JTextField();
		txtNombre.setBounds(83, 33, 151, 20);
		contentPane.add(txtNombre);
		txtNombre.setColumns(10);

		txtEmail = new JTextField();
		txtEmail.setBounds(83, 64, 167, 20);
		contentPane.add(txtEmail);
		txtEmail.setColumns(10);

		txtTelefono = new JTextField();
		txtTelefono.setBounds(83, 92, 167, 20);
		contentPane.add(txtTelefono);
		txtTelefono.setColumns(10);

		JLabel lblNewLabel_1_1 = new JLabel("EMAIL :");
		lblNewLabel_1_1.setBounds(10, 64, 65, 14);
		contentPane.add(lblNewLabel_1_1);

		JLabel lblNewLabel_1_2 = new JLabel("TELEFONO :");
		lblNewLabel_1_2.setBounds(10, 95, 65, 14);
		contentPane.add(lblNewLabel_1_2);

		JLabel lblNewLabel_2 = new JLabel("GRADO :");
		lblNewLabel_2.setBounds(10, 130, 65, 14);
		contentPane.add(lblNewLabel_2);

		cboGrados = new JComboBox();
		cboGrados.setBounds(83, 123, 139, 22);
		contentPane.add(cboGrados);

		JLabel lblNewLabel_3 = new JLabel("MODALIDAD");
		lblNewLabel_3.setBounds(10, 161, 73, 14);
		contentPane.add(lblNewLabel_3);

		cboModalidades = new JComboBox();
		cboModalidades.setBounds(93, 157, 129, 22);
		contentPane.add(cboModalidades);

		btnRegistrar = new JButton("Registrar");
		btnRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				registrar();
			}

		});
		btnRegistrar.setBounds(10, 235, 89, 23);
		contentPane.add(btnRegistrar);

		JButton btnNewButton = new JButton("Ventana de Reservas");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reservas();
			}

		});
		btnNewButton.setBounds(145, 235, 151, 23);
		contentPane.add(btnNewButton);

		lblNewLabel_4 = new JLabel("Fecha");
		lblNewLabel_4.setBounds(10, 196, 56, 14);
		contentPane.add(lblNewLabel_4);

		txtFecha = new JTextField();
		txtFecha.setEditable(false);
		txtFecha.setBounds(83, 190, 151, 20);
		contentPane.add(txtFecha);
		txtFecha.setColumns(10);

		llenarCombos();
		fecha();
	}

	void llenarCombos() {

		EntityManagerFactory conexion = Persistence.createEntityManagerFactory("LP2_T1_DIAZ");
		EntityManager manager = conexion.createEntityManager();

		String jpql = "select m from Modalidad m";
		List<Modalidad> lstModalidades = manager.createQuery(jpql, Modalidad.class).getResultList();
		cboModalidades.addItem("-Seleccione-");
		for (Modalidad modalidad : lstModalidades) {
			cboModalidades.addItem(modalidad.getDescripcion());
		}

		String jpql2 = "select g from Grado g";
		List<Grado> lstGrados = manager.createQuery(jpql2, Grado.class).getResultList();
		cboGrados.addItem("-Seleccione-");
		for (Grado grado : lstGrados) {
			cboGrados.addItem(grado.getDescripcion());
		}

	}

	private void registrar() {
		EntityManagerFactory conexion = Persistence.createEntityManagerFactory("LP2_T1_DIAZ");
		EntityManager manager = conexion.createEntityManager();

		Reserva r = new Reserva();

		String nombre = leerNombre();
		String email = leerEmail();
		String telefono = leerTelefono();
		String fecha = leerFecha();
		String dni = leerDni();
		int modalidad = leerModalidades();
		int grados = leerGrados();

		r.setDni(dni);
		r.setNombre_alumno(nombre);
		r.setEmail(email);
		r.setTelefono(telefono);
		r.setId_grado(grados);
		r.setId_modalidad(modalidad);
		r.setFecha_inscripcion(fecha);

		try {
			manager.getTransaction().begin();
			manager.persist(r);
			manager.getTransaction().commit();
			limpiar();
			mensaje("Registro Ok");
		} catch (Exception e) {
			mensaje("Error: " + e.getCause().getMessage());
			mensaje("No se pudo realizar la reserva");
		}

	}

	private void reservas() {
		RegistrosDiaz rd = new RegistrosDiaz();
		rd.setVisible(true);
		rd.setLocationRelativeTo(this);

	}

	void fecha() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		LocalDateTime now = LocalDateTime.now();
		txtFecha.setText(dtf.format(now));
	}

	void limpiar() {
		txtNombre.setText("");
		txtEmail.setText("");
		txtTelefono.setText("");
		txtDni.setText("");
		txtFecha.setText("");
		cboGrados.setSelectedIndex(0);
		cboModalidades.setSelectedIndex(0);
	}

	void mensaje(String s) {
		JOptionPane.showMessageDialog(this, s);
	}

	String leerNombre() {
		return txtNombre.getText();
	}

	String leerEmail() {
		return txtEmail.getText();
	}

	String leerTelefono() {
		return txtTelefono.getText();
	}

	String leerDni() {
		return txtDni.getText();
	}

	String leerFecha() {
		return txtFecha.getText();
	}

	int leerModalidades() {
		return cboModalidades.getSelectedIndex();
	}

	int leerGrados() {
		return cboGrados.getSelectedIndex();
	}
}
