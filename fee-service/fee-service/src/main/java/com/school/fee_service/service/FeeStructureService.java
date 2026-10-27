package com.school.fee_service.service;

import com.school.fee_service.dto.FeeStructureRequest;
import com.school.fee_service.dto.FeeStructureResponse;
import com.school.fee_service.entity.FeeStructure;
import com.school.fee_service.repository.FeeStructureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeeStructureService {

    @Autowired
    private FeeStructureRepository feeStructureRepository;

    // The class lives in academic-service, so only its id is stored here.
    // Checking that the class id exists will be added in Step 6 (OpenFeign).

    public FeeStructureResponse createFeeStructure(FeeStructureRequest request) {
        FeeStructure fee = new FeeStructure();
        fee.setClassId(request.getClassId());
        fee.setFeeName(request.getFeeName());
        fee.setAmount(request.getAmount());
        fee.setFrequency(request.getFrequency());

        FeeStructure saved = feeStructureRepository.save(fee);
        return FeeStructureResponse.fromEntity(saved);
    }

    public List<FeeStructureResponse> getByClass(Long classId) {
        return feeStructureRepository.findByClassId(classId)
                .stream().map(FeeStructureResponse::fromEntity).collect(Collectors.toList());
    }

    public void deleteFeeStructure(Long id) {
        if (!feeStructureRepository.existsById(id)) {
            throw new RuntimeException("Fee structure not found with id: " + id);
        }
        feeStructureRepository.deleteById(id);
    }
}