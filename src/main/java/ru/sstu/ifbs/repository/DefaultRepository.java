package ru.sstu.ifbs.repository;

import io.jmix.core.DataManager;
import io.jmix.core.FetchPlan;
import io.jmix.core.FetchPlanBuilder;
import io.jmix.core.FluentLoader;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

public abstract class DefaultRepository<T> {

    @Autowired
    protected DataManager dataManager;

    protected abstract Class<T> entityClass();

    public FluentLoader<T> load() {
        return dataManager.load(entityClass());
    }

    public Optional<T> findById(UUID id) {
        return load()
                .query("where id = ?", id)
                .optional();
    }

    public List<T> getByIds(Collection<?> ids, FetchPlan fetchPlan) {
        return load()
                .query("where e.id in :ids")
                .parameter("ids", ids)
                .fetchPlan(fetchPlan)
                .list();
    }

    public List<T> getByIdsExcluded(Collection<?> ids, FetchPlan fetchPlan) {
        return load()
                .query("where e.id not in :ids")
                .parameter("ids", ids)
                .fetchPlan(fetchPlan)
                .list();
    }
}
