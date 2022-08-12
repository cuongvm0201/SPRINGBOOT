package com.example.batdongsan.repository;

import com.example.batdongsan.entity.DanhMuc;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DanhMucRepository  extends CrudRepository<DanhMuc,Integer> {
}
