package cz.airbank.cucumber.reports.view.reports.bean.support;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.inject.Named;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cz.airbank.cucumber.reports.view.reports.service.EmbeddingService;

/**
 * Support bean, which provide image content to render inside JSF.
 *
 * @author Vaclav Stengl
 */
@Named
@Component
public class ImageBean {

    private final EmbeddingService embeddingService;

    @Autowired
    public ImageBean(EmbeddingService embeddingService) {
        this.embeddingService = embeddingService;
    }

    /**
     * Support method which provide image content to primefaces image component - component require field as data source for image so
     * this method is fake getter for image field.
     * Image id is obtained from parameter with name {@link #getImageIdParamName()} and used to retrieve image from data source.
     *
     * @return new instance of {@link StreamedContent} when page is in {@link PhaseId#RENDER_RESPONSE},
     * real content otherwise
     */
    public StreamedContent getImage() {
        FacesContext context = FacesContext.getCurrentInstance();

        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            // So, we're rendering the view. Return a stub StreamedContent so that it will generate right URL.
            return new DefaultStreamedContent();
        }

        // So, browser is requesting the image. Get ID value from actual request param.
        String id = context.getExternalContext().getRequestParameterMap().get(getImageIdParamName());
        return embeddingService.retrieveImage(id);
    }

    /**
     * Name of parameter with image id.
     */
    public String getImageIdParamName() {
        return "imgId";
    }
}
