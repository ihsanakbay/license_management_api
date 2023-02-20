package dev.ihsanakbay.license_management_api.entities.requests;

public record FileRequest(
        String id,
        String name,
        String folderPath,
        String licenseId
) {
}
