package wad.domain;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

@Entity
public class Image extends AbstractUUIDPersistable {

    private String metadata;

    @Basic(fetch = FetchType.LAZY)
    @OneToOne
    private FileObject original;

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public FileObject getOriginal() {
        return original;
    }

    public void setOriginal(FileObject original) {
        this.original = original;
    }
}

