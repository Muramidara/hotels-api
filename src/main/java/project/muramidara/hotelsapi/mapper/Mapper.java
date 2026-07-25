package project.muramidara.hotelsapi.mapper;

public interface Mapper<F, T> {
    T map(F entity);

}
