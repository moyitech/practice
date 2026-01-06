package com.carbon.assets.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 资产上传审批VO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssetUploadApproval implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String userName;

    private String agenciesName;

    private String contactNumber;

    private String assetType;

    private String primaryMarketHoldingInstitutions;

    private String shareholding;

    private String proofOfPosition;

    private String issuingAgency;

}

