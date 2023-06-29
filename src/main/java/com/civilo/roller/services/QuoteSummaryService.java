package com.civilo.roller.services;

import com.civilo.roller.Entities.QuoteEntity;
import com.civilo.roller.Entities.QuoteSummaryEntity;
import com.civilo.roller.Entities.SellerEntity;
import com.civilo.roller.repositories.QuoteSummaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class QuoteSummaryService {
    @Autowired
    QuoteSummaryRepository quoteSummaryRepository;

    @Autowired
    IVAService ivaService;

    // Permite guardar un objeto del tipo "QuoteSummaryEntity" en la base de datos.
    public QuoteSummaryEntity saveQuoteSummary(QuoteSummaryEntity quoteSummary){
        return quoteSummaryRepository.save(quoteSummary);
    }

    //
    public QuoteSummaryEntity summaryCalculation(List<QuoteEntity> quoteEntities, Long quoteSummaryID){
        QuoteSummaryEntity quoteSummary = new QuoteSummaryEntity();
        float totalCostOfProduction = 0, totalSaleValue = 0, valueAfterDiscount = 0, discountPercentage = 0, totalNet = 0, iva = 0, ivaPercentage = 0, total = 0;
        Date date = null;
        SellerEntity seller = null;
        for (int i = 0; i < quoteEntities.size(); i++) {
            totalCostOfProduction += quoteEntities.get(i).getProductionCost();
            totalSaleValue += quoteEntities.get(i).getSaleValue();
            discountPercentage = quoteEntities.get(i).getPercentageDiscount();
            date = quoteEntities.get(i).getDate();
            seller = quoteEntities.get(i).getSeller();
        }
        ivaPercentage = ivaService.getLastIVA().getIvaPercentage();
        valueAfterDiscount = totalSaleValue;
        totalNet = totalSaleValue;
        if (discountPercentage != 0) {
            valueAfterDiscount = (float) Math.ceil(totalSaleValue * (discountPercentage / 100));
            totalNet = totalSaleValue - valueAfterDiscount;
        }
        iva = totalNet;
        if (ivaPercentage != 0) {
            iva = (float) Math.ceil(totalNet * (ivaPercentage / 100));
        }
        total = (float) Math.ceil(totalNet * (1 + ivaPercentage / 100));
        quoteSummary.setDate(date);
        quoteSummary.setSeller(seller);
        quoteSummary.setTotalCostOfProduction((int) Math.ceil(totalCostOfProduction));
        quoteSummary.setTotalSaleValue((int) Math.ceil(totalSaleValue));
        quoteSummary.setValueAfterDiscount((int) Math.ceil(valueAfterDiscount));
        quoteSummary.setNetTotal((int) Math.ceil(totalNet));
        quoteSummary.setTotal((int) Math.ceil(total));
        quoteSummary.setCurrentIVA(ivaService.getIVAByPercentage(ivaPercentage));

        if (quoteSummaryID != null){
            quoteSummary.setQuoteSummaryID(quoteSummaryID);
        }
        return quoteSummaryRepository.save(quoteSummary);
    }
}