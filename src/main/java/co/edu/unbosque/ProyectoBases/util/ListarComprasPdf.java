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

import co.edu.unbosque.ProyectoBases.MariaDB.model.Compra;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class ListarComprasPdf extends AbstractPdfView {

	@Override
	public void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		@SuppressWarnings("unchecked")
		List<Compra> lista = (List<Compra>) model.get("compras");
		document.open();
		Font tituloFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, Color.BLUE);
		document.add(new Phrase("Listado General de Compras\n\n", tituloFont));
		PdfPTable tablaCompras = new PdfPTable(5);
		tablaCompras.setWidthPercentage(100);
		tablaCompras.setSpacingBefore(10);
		tablaCompras.setSpacingAfter(10);
		tablaCompras.setWidths(new float[] { 1f, 2f, 1.5f, 2f, 2f });
		Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, Color.WHITE);
		PdfPCell celdaHeader;

		String[] columnas = { "ID", "Cliente", "Precio", "Fecha", "Propiedad" };
		for (String columna : columnas) {
			celdaHeader = new PdfPCell(new Phrase(columna, headerFont));
			celdaHeader.setBackgroundColor(new Color(0, 51, 102)); // Color azul oscuro
			celdaHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
			celdaHeader.setVerticalAlignment(Element.ALIGN_MIDDLE);
			celdaHeader.setPadding(10);
			tablaCompras.addCell(celdaHeader);
		}
		Font dataFont = FontFactory.getFont(FontFactory.HELVETICA, 10, Color.BLACK);
		for (Compra compra : lista) {
			PdfPCell celdaDato = new PdfPCell(new Phrase(compra.getId().toString(), dataFont));
			celdaDato.setHorizontalAlignment(Element.ALIGN_CENTER);
			celdaDato.setPadding(8);
			tablaCompras.addCell(celdaDato);
			String nombreCliente = compra.getIdCliente() != null ? compra.getIdCliente().getNombre() : "Desconocido";
			celdaDato = new PdfPCell(new Phrase(nombreCliente, dataFont));
			celdaDato.setPadding(8);
			tablaCompras.addCell(celdaDato);
			celdaDato = new PdfPCell(new Phrase(String.format("$%.2f", compra.getPrecio()), dataFont));
			celdaDato.setHorizontalAlignment(Element.ALIGN_RIGHT);
			celdaDato.setPadding(8);
			tablaCompras.addCell(celdaDato);
			celdaDato = new PdfPCell(new Phrase(compra.getFecha().toString(), dataFont));
			celdaDato.setHorizontalAlignment(Element.ALIGN_CENTER);
			celdaDato.setPadding(8);
			tablaCompras.addCell(celdaDato);
			String propiedad = (compra.getIdCasa() != null ? "Casa ID: " + compra.getIdCasa() : "")
					+ (compra.getIdApartamento() != null ? "Apartamento ID: " + compra.getIdApartamento()
							: "Sin especificar");
			celdaDato = new PdfPCell(new Phrase(propiedad, dataFont));
			celdaDato.setPadding(8);
			tablaCompras.addCell(celdaDato);
		}
		document.add(tablaCompras);
		document.close();
	}
}
