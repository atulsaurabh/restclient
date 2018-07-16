/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.suyojan.system.restclient.repository;

import java.util.List;
import net.suyojan.system.restclient.entity.Testrecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Suyojan
 */

@Repository
public interface TestRecordRepository extends JpaRepository<Testrecord, Long>
{
    List<Testrecord> findTop25BySent(boolean sent);  
}
