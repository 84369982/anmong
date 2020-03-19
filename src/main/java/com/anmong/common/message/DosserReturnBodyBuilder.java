package com.anmong.common.message;


import com.anmong.common.constrant.HttpStatusCode;
import com.anmong.common.util.MapBuilder;
import com.anmong.common.util.MapFactory;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class DosserReturnBodyBuilder {
    private DosserReturnBody dosserReturnBody = new DosserReturnBody();
    private HttpServletResponse response = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();

    public DosserReturnBodyBuilder() {
        this.dosserReturnBody.setCode(HttpStatusCode.STATUS_OK.code);
        this.dosserReturnBody.setMessage(HttpStatusCode.STATUS_OK.defaultMessage);
        this.response.setStatus(HttpStatusCode.STATUS_OK.status.intValue());
    }

    public DosserReturnBodyBuilder status(HttpStatusCode statusCode) {
        this.dosserReturnBody.setCode(statusCode.code);
        if (this.dosserReturnBody.getMessage() == null) {
            this.dosserReturnBody.setMessage(statusCode.defaultMessage);
        }

        this.response.setStatus(statusCode.status.intValue());
        return this;
    }

    public DosserReturnBodyBuilder statusOk() {
        HttpStatusCode statusCode = HttpStatusCode.STATUS_OK;
        this.dosserReturnBody.setCode(statusCode.code);
        if (this.dosserReturnBody.getMessage() == null) {
            this.dosserReturnBody.setMessage(statusCode.defaultMessage);
        }

        this.response.setStatus(statusCode.status.intValue());
        return this;
    }

    public DosserReturnBodyBuilder statusCreated() {
        HttpStatusCode statusCode = HttpStatusCode.STATUS_CREATED;
        this.dosserReturnBody.setCode(statusCode.code);
        if (this.dosserReturnBody.getMessage() == null) {
            this.dosserReturnBody.setMessage(statusCode.defaultMessage);
        }

        this.response.setStatus(statusCode.status.intValue());
        return this;
    }

    public DosserReturnBodyBuilder statusAccpted() {
        HttpStatusCode statusCode = HttpStatusCode.STATUS_ACCEPTED;
        this.dosserReturnBody.setCode(statusCode.code);
        if (this.dosserReturnBody.getMessage() == null) {
            this.dosserReturnBody.setMessage(statusCode.defaultMessage);
        }

        this.response.setStatus(statusCode.status.intValue());
        return this;
    }

    public DosserReturnBodyBuilder statusNoContent() {
        HttpStatusCode statusCode = HttpStatusCode.STATUS_NO_CONTENT;
        this.dosserReturnBody.setCode(statusCode.code);
        if (this.dosserReturnBody.getMessage() == null) {
            this.dosserReturnBody.setMessage(statusCode.defaultMessage);
        }

        this.response.setStatus(statusCode.status.intValue());
        return this;
    }

    public DosserReturnBodyBuilder statusBadRequest() {
        HttpStatusCode statusCode = HttpStatusCode.STATUS_BAD_REQUEST;
        this.dosserReturnBody.setCode(statusCode.code);
        if (this.dosserReturnBody.getMessage() == null) {
            this.dosserReturnBody.setMessage(statusCode.defaultMessage);
        }

        this.response.setStatus(statusCode.status.intValue());
        return this;
    }

    public DosserReturnBodyBuilder statusUnAuthorized() {
        HttpStatusCode statusCode = HttpStatusCode.STATUS_UN_AUTHORIZED;
        this.dosserReturnBody.setCode(statusCode.code);
        if (this.dosserReturnBody.getMessage() == null) {
            this.dosserReturnBody.setMessage(statusCode.defaultMessage);
        }

        this.response.setStatus(statusCode.status.intValue());
        return this;
    }

    public DosserReturnBodyBuilder statusForbidden() {
        HttpStatusCode statusCode = HttpStatusCode.STATUS_FORBIDDEN;
        this.dosserReturnBody.setCode(statusCode.code);
        if (this.dosserReturnBody.getMessage() == null) {
            this.dosserReturnBody.setMessage(statusCode.defaultMessage);
        }

        this.response.setStatus(statusCode.status.intValue());
        return this;
    }

    public DosserReturnBodyBuilder statusNotFound() {
        HttpStatusCode statusCode = HttpStatusCode.STATUS_NOT_FOUND;
        this.dosserReturnBody.setCode(statusCode.code);
        if (this.dosserReturnBody.getMessage() == null) {
            this.dosserReturnBody.setMessage(statusCode.defaultMessage);
        }

        this.response.setStatus(statusCode.status.intValue());
        return this;
    }

    public DosserReturnBodyBuilder statusConflict() {
        HttpStatusCode statusCode = HttpStatusCode.STATUS_CONFLICT;
        this.dosserReturnBody.setCode(statusCode.code);
        if (this.dosserReturnBody.getMessage() == null) {
            this.dosserReturnBody.setMessage(statusCode.defaultMessage);
        }

        this.response.setStatus(statusCode.status.intValue());
        return this;
    }

    public DosserReturnBodyBuilder statusInternalServerError() {
        HttpStatusCode statusCode = HttpStatusCode.STATUS_INTERNAL_SERVER_ERROR;
        this.dosserReturnBody.setCode(statusCode.code);
        if (this.dosserReturnBody.getMessage() == null) {
            this.dosserReturnBody.setMessage(statusCode.defaultMessage);
        }

        this.response.setStatus(statusCode.status.intValue());
        return this;
    }

    public DosserReturnBodyBuilder collection(List collection) {
        this.dosserReturnBody.setCollection(collection);
        this.dosserReturnBody.setSize(collection.size());
        return this;
    }

    public DosserReturnBodyBuilder collectionItem(Object item) {
        this.dosserReturnBody.setCollection(Lists.newArrayList(new Object[]{item}));
        this.dosserReturnBody.setSize(1);
        return this;
    }

    public DosserReturnBodyBuilder success(boolean isSuccess) {
        this.dosserReturnBody.setSuccess(isSuccess);
        return this;
    }

    public DosserReturnBodyBuilder size(long size) {
        this.dosserReturnBody.setSize((int)size);
        return this;
    }

    public DosserReturnBodyBuilder size(int size) {
        this.dosserReturnBody.setSize(size);
        return this;
    }

    /** @deprecated */
    @Deprecated
    public DosserReturnBodyBuilder criteria(Map criteria) {
        this.dosserReturnBody.addCriteria(criteria);
        return this;
    }

    /** @deprecated */
    @Deprecated
    public DosserReturnBodyBuilder criteria(Object dto) {
        this.dosserReturnBody.addCriteria(dto);
        return this;
    }

    /** @deprecated */
    @Deprecated
    public DosserReturnBodyBuilder criteriaMap(String k1, Object v1) {
        this.dosserReturnBody.addCriteria(MapFactory.of(k1, v1));
        return this;
    }

    public DosserReturnBodyBuilder paginate(int currentPage, int perPage, long totalItems) {
        return this.paginate(currentPage, perPage, (int)totalItems);
    }

    public DosserReturnBodyBuilder paginate(int currentPage, int perPage, int totalItems) {
        this.dosserReturnBody.setSize(totalItems);
        if (totalItems < 0) {
            throw new RuntimeException("The totalItems argument should be a positive integer.");
        } else if (perPage <= 0) {
            throw new RuntimeException("The perPage argument should be a positive integer.");
        } else {
            if (currentPage < 0) {
                currentPage = 1;
            }

            int totalPages = totalItems / perPage + (totalItems % perPage > 0 ? 1 : 0);
            currentPage = Integer.min(currentPage, totalPages);
            int minItemOnCurrentPage = perPage * (currentPage - 1) + 1;
            int maxItemOnCurrentPage = Integer.min(perPage * currentPage, totalItems);
            int itemsOnCurrentPage = maxItemOnCurrentPage - minItemOnCurrentPage + 1;
            if (0 == totalItems) {
                totalPages = 1;
                currentPage = 1;
                minItemOnCurrentPage = 0;
                maxItemOnCurrentPage = 0;
                itemsOnCurrentPage = 0;
            }

            Map<String, Object> pagination = (new MapBuilder()).add("total_items", totalItems).add("per_page", perPage).add("current_page", currentPage).add("total_pages", totalPages).add("min_item_on_current_page", minItemOnCurrentPage).add("max_item_on_current_page", maxItemOnCurrentPage).add("items_on_current_page", itemsOnCurrentPage).build();
            this.dosserReturnBody.addPagination(pagination);
            return this;
        }
    }

    public DosserReturnBodyBuilder message(String message) {
        this.dosserReturnBody.setMessage(message);
        return this;
    }

    public DosserReturnBodyBuilder errors(Map<String, List<String>> errors) {
        this.dosserReturnBody.setErrors(errors);
        return this;
    }

    public DosserReturnBody build() {
        return this.dosserReturnBody;
    }
    
    public DosserReturnBodyBuilder notInRule(String code) {
        HttpStatusCode statusCode = HttpStatusCode.STATUS_OK;
        this.dosserReturnBody.setSuccess(false);
        this.dosserReturnBody.setCode(code);
        this.response.setStatus(statusCode.status.intValue());
        return this;
    } 
    
    public DosserReturnBodyBuilder businessException(String code) {
        HttpStatusCode statusCode = HttpStatusCode.STATUS_OK;
        this.dosserReturnBody.setSuccess(false);
        this.dosserReturnBody.setCode(code);
        this.response.setStatus(statusCode.status.intValue());
        return this;
    } 
}
