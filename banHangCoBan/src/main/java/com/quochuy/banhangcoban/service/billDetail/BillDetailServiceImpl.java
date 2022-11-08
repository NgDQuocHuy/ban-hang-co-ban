package com.quochuy.banhangcoban.service.billDetail;

import com.quochuy.banhangcoban.model.BillDetail;
import com.quochuy.banhangcoban.repository.BillDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BillDetailServiceImpl implements IBillDetailService {

    @Autowired
    private BillDetailRepository billDetailRepository;

    @Override
    public List<BillDetail> findAll() {
        return null;
    }

    @Override
    public BillDetail getById(Long id) {
        return null;
    }

    @Override
    public Optional<BillDetail> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public BillDetail save(BillDetail billDetail) {
        return billDetailRepository.save(billDetail);
    }

    @Override
    public void remove(Long id) {

    }
}
