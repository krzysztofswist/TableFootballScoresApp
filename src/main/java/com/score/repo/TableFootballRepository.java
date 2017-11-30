package com.score.repo;

import org.springframework.data.repository.CrudRepository;
import com.score.model.tablefootball.TableFootballScore;

public interface TableFootballRepository extends CrudRepository<TableFootballScore, Long> {

}
