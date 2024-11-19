package co.edu.unbosque.ProyectoBases.util;

import java.awt.Color;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import co.edu.unbosque.ProyectoBases.MariaDB.model.Venta;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class ListarVentasPdf extends AbstractPdfView {

	@Override
	public void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		@SuppressWarnings("unchecked")
		List<Venta> lista = (List<Venta>) model.get("ventas");

		// Configuración básica del documento
		document.open();

		// Título del documento
		Font tituloFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, Color.BLUE);
		document.add(new Phrase("Listado General de Ventas\n\n", tituloFont));

		// Crear la tabla de ventas
		PdfPTable tablaVentas = new PdfPTable(5); // 5 columnas: ID, Cliente, Precio, Fecha, Propiedad
		tablaVentas.setWidthPercentage(100);
		tablaVentas.setSpacingBefore(10);
		tablaVentas.setSpacingAfter(10);
		tablaVentas.setWidths(new float[] { 1f, 2f, 1.5f, 2f, 2f });

		// Encabezados de la tabla
		Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, Color.WHITE);
		PdfPCell celdaHeader;
		String[] columnas = { "ID", "Cliente", "Precio", "Fecha", "Propiedad" };
		for (String columna : columnas) {
			celdaHeader = new PdfPCell(new Phrase(columna, headerFont));
			celdaHeader.setBackgroundColor(new Color(0, 51, 102)); // Color azul oscuro
			celdaHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
			celdaHeader.setVerticalAlignment(Element.ALIGN_MIDDLE);
			celdaHeader.setPadding(10);
			tablaVentas.addCell(celdaHeader);
		}

		// Rellenar la tabla con los datos de las ventas
		Font dataFont = FontFactory.getFont(FontFactory.HELVETICA, 10, Color.BLACK);
		for (Venta venta : lista) {
			// ID de la venta
			PdfPCell celdaDato = new PdfPCell(new Phrase(venta.getId().toString(), dataFont));
			celdaDato.setHorizontalAlignment(Element.ALIGN_CENTER);
			celdaDato.setPadding(8);
			tablaVentas.addCell(celdaDato);

			// Cliente asociado
			String nombreCliente = venta.getIdCliente() != null ? venta.getIdCliente().getNombre() : "Desconocido";
			celdaDato = new PdfPCell(new Phrase(nombreCliente, dataFont));
			celdaDato.setPadding(8);
			tablaVentas.addCell(celdaDato);

			// Precio de la venta
			celdaDato = new PdfPCell(new Phrase(String.format("$%.2f", venta.getPrecio()), dataFont));
			celdaDato.setHorizontalAlignment(Element.ALIGN_RIGHT);
			celdaDato.setPadding(8);
			tablaVentas.addCell(celdaDato);

			// Fecha de la venta
			celdaDato = new PdfPCell(new Phrase(venta.getFecha().toString(), dataFont));
			celdaDato.setHorizontalAlignment(Element.ALIGN_CENTER);
			celdaDato.setPadding(8);
			tablaVentas.addCell(celdaDato);

			// Propiedad vendida
			String propiedad = (venta.getIdCasa() != null ? "Casa ID: " + venta.getIdCasa() : "")
					+ (venta.getIdApartamento() != null ? "Apartamento ID: " + venta.getIdApartamento()
							: "Sin especificar");
			celdaDato = new PdfPCell(new Phrase(propiedad, dataFont));
			celdaDato.setPadding(8);
			tablaVentas.addCell(celdaDato);
		}

		// Añadir la tabla al documento
		document.add(tablaVentas);

		// Cerrar el documento
		document.close();
	}
}
