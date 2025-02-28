package hieu.javarestapi.model.response;

import lombok.experimental.SuperBuilder;

@SuperBuilder
public class PageResponseAbstract {
    public int totalElements;
    public int pageNumber;
    public int pageSize;
    public int totalPages;
}
