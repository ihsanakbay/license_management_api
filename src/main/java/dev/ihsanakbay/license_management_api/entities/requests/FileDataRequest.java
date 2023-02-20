package dev.ihsanakbay.license_management_api.entities.requests;

public record FileDataRequest(
        String name,
        String folderPath,
        String licenseId,
        Boolean status
) {
}
