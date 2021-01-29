package com.fy.common.repository;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;

@NoRepositoryBean
public interface BaseRepository<T, ID> extends ReactiveSortingRepository<T, ID> {
}
