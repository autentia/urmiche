package com.autentia.view.urmiche;

import com.autentia.domain.urmiche.Link;
import com.autentia.domain.urmiche.LinksInspector;
import com.autentia.domain.urmiche.http.HttpConnectionPool;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.List;

import static javax.faces.application.FacesMessage.SEVERITY_ERROR;

@ManagedBean
@ViewScoped
public class InspectUrlsView {

    @ManagedProperty(value = "#{httpConnectionPool}")
    private HttpConnectionPool httpConnectionPool;

    public void setHttpConnectionPool(HttpConnectionPool httpConnectionPool) {
        this.httpConnectionPool = httpConnectionPool;
    }

    private LinksInspector linksInspector;

    @PostConstruct
    public void init() {
        linksInspector = new LinksInspector(httpConnectionPool);
    }

    public List<Link> getLinks() {
        return linksInspector.getLinks();
    }

    /**
     * *****************************************
     * Upload
     * *******************************************
     */
    private UploadedFile file;

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public void upload(FileUploadEvent event) {
        final UploadedFile file = event.getFile();
        if (file == null) return;

        FacesMessage message;
        try {
            linksInspector.from(file.getInputstream());

            message = new FacesMessage("Leídas con éxito " + linksInspector.getLinks().size() + " URLs del fichero: " + file.getFileName(), "");

        } catch (IOException e) {
            message = new FacesMessage(SEVERITY_ERROR, "No se puede leer el fichero: " + file.getFileName(), e.getLocalizedMessage());
            e.printStackTrace();
        }

        FacesContext.getCurrentInstance().addMessage(null, message);
    }


    /********************************************
     * Filter
     *********************************************/


    /**
     * *****************************************
     * Inspect
     * *******************************************
     */

    public void inspect() {
        linksInspector.inspect();
        List<Link> links = linksInspector.getLinks();
        System.out.println("*** URLs init");
        for (Link link: links) {
            System.out.println(link.getUrl() + ", " + link.getLastUrl());
        }
        System.out.println("*** URLs end");
    }

}
