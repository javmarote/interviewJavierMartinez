package com.vonage.api.interview.data.repository;

import com.vonage.api.interview.data.model.Document;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;

import java.util.ArrayList;
import java.util.List;

public class DocumentRepositoryImpl implements DocumentRepositoryCustom {

	@PersistenceContext
	private EntityManager em;

	// Method to obtain all the documents existings in the database
	public List<Document> getDocuments() {

		List<Document> documents = new ArrayList<Document>();

		Query nativeQuery = em.createNativeQuery("SELECT * FROM DOCUMENT", Document.class);

        documents = null;

		try {
            documents = (List<Document>) nativeQuery.getResultList();
		} catch (Exception e) {
			e.getMessage();
		}

		return documents;

	}

	public List<Document> searchDocument(String name) {
		List<Document> documents = getDocuments();
		List<Document> documentsSearch = new ArrayList<>();
		documents.stream().forEach( document -> {
			if (document.getName().contains(name)) {
				documentsSearch.add(document);
			}
		});

		return  documentsSearch;
	}

	@Modifying
	@Transactional
	public Document addDocument(String name, int pages) {
		// parameterized query to avoid sql injection
		Query nativeQuery = em.createNativeQuery(
				"insert into DOCUMENT (name, pages) values (?1, ?2);",
				Document.class);

		Document document = null;
		nativeQuery.setParameter(1, name);
		nativeQuery.setParameter(2, pages);

		try {
			 em.joinTransaction();
			 nativeQuery.executeUpdate();
		} catch (Exception e) {
			e.getMessage();
		}

		return document;

	}
}
