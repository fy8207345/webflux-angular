package com.fy.common.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;

import java.util.Date;

public class BaseService {

    protected static Criteria eq(String field, String value){
        return Criteria.where(field).is(value);
    }

    protected static Criteria eq(String field, Integer value){
        return Criteria.where(field).is(value);
    }

    protected static Criteria eq(String field, Long value){
        return Criteria.where(field).is(value);
    }

    protected static Criteria like(String field, String value){
        return Criteria.where(field).like("%" + value + "%");
    }

    protected static Criteria gte(String field, Date date){
        return Criteria.where(field).greaterThanOrEquals(date);
    }

    protected static Criteria gte(String field, Integer date){
        return Criteria.where(field).greaterThanOrEquals(date);
    }

    protected static Criteria gte(String field, Long date){
        return Criteria.where(field).greaterThanOrEquals(date);
    }

    protected static Criteria lte(String field, Date date){
        return Criteria.where(field).lessThanOrEquals(date);
    }

    protected static Criteria lte(String field, Integer date){
        return Criteria.where(field).greaterThanOrEquals(date);
    }

    protected static Criteria lte(String field, Long date){
        return Criteria.where(field).greaterThanOrEquals(date);
    }

    protected static Query query(Pageable pageable, Criteria... criterias){
        return Query.query(Criteria.from(criterias)).with(pageable);
    }

    protected static Query query(Sort sort, Pageable pageable, Criteria... criterias){
        return Query.query(Criteria.from(criterias)).with(pageable).sort(sort);
    }
}
