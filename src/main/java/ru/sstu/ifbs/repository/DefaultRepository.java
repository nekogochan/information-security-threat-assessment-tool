package ru.sstu.ifbs.repository;

import io.jmix.core.DataManager;
import io.jmix.core.FluentLoader;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;

public abstract class DefaultRepository<T> {

    @Autowired
    private DataManager dataManager;

    protected abstract Class<T> entityClass();

    public FluentLoader<T> load() {
        return dataManager.load(entityClass());
    }

    public List<T> getByIds(Collection<?> ids) {
        return load()
                .query("where e.id in :ids")
                .parameter("ids", ids)
                .list();
    }

    public List<T> getByIdsExcluded(Collection<?> ids) {
        return load()
                .query("where e.id not in :ids")
                .parameter("ids", ids)
                .list();
    }
}
