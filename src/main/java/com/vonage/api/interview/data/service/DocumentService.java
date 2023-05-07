package com.vonage.api.interview.data.service;

import com.vonage.api.interview.data.model.Document;
import com.vonage.api.interview.data.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DocumentService {

    @Autowired
    DocumentRepository documentRepository;

    public List<Document> getDocuments() {
        List<Document> documents = new ArrayList<Document>();
        documentRepository.findAll().forEach(document -> documents.add(document));
        return documents;
    }

    public void saveOrUpdate(Document document) {
        documentRepository.save(document);
    }

    public void delete(Long id) {
        documentRepository.deleteById(id);
    }

    public void update(Document document, Long documentId) {
        documentRepository.save(document);
    }

    public Document getDocumentById(Long id) {
        return documentRepository.findById(id).get();
    }
}
