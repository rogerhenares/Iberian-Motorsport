package com.iberianmotorsports.service.repository;

import com.iberianmotorsports.service.model.GridUser;
import com.iberianmotorsports.service.model.composeKey.GridUserPrimaryKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GridUserRepository extends JpaRepository<GridUser, GridUserPrimaryKey> {

    GridUser findGridUserByPrimaryKeyGridIdAndGridManagerTrue(Integer gridId);
}
