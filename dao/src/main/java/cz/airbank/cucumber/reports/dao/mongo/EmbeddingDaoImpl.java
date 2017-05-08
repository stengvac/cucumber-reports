package cz.airbank.cucumber.reports.dao.mongo;

import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Component;

import cz.airbank.cucumber.reports.dao.EmbeddingDao;
import cz.airbank.cucumber.reports.dao.to.EmbeddingTo;

/**
 * Implementation of {@link EmbeddingDao}
 *
 * @author Vaclav Stengl
 */
@Component
public class EmbeddingDaoImpl implements EmbeddingDao {

    private final GridFsTemplate gridFsTemplate;

    @Autowired
    public EmbeddingDaoImpl(GridFsTemplate gridFsTemplate) {
        this.gridFsTemplate = gridFsTemplate;
    }

    @Override
    public EmbeddingTo findById(String id) {
        GridFSDBFile file = gridFsTemplate.findOne(new Query().addCriteria(Criteria.where("_id").is(id)));

        if (file == null) {
            return null;
        }

        return new EmbeddingTo(file.getInputStream(), file.getContentType());
    }

    @Override
    public String store(InputStream stream, String contentType) {
        GridFSFile file = gridFsTemplate.store(stream, null, contentType);

        return file.getId().toString();
    }
}
