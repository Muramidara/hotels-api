package project.muramidara.hotelsapi.mapper;

public interface BiDirectionalMapper<E, D> {
    D map(E entity);
    E mapFrom(D dto);
}
