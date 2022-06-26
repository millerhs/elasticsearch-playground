package millerhs.elastic.services;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

@Service
public class TikaService {
	
	/**
	 * Extract plain text from files. This has been confirmed to work with TXT, DOCX, XLSX, and PDF.
	 */
	public String extractText(byte[] file) throws IOException, SAXException, TikaException {
		AutoDetectParser parser = new AutoDetectParser();
		BodyContentHandler handler = new BodyContentHandler();
	    Metadata metadata = new Metadata();
	    try (InputStream stream = new ByteArrayInputStream(file)) {
	        parser.parse(stream, handler, metadata);
	        return handler.toString();
	    }
	}
	
}
