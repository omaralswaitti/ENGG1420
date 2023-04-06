package com.mycompany.project;

import com.laserfiche.api.client.model.AccessKey;
import com.laserfiche.repository.api.RepositoryApiClient;
import com.laserfiche.repository.api.RepositoryApiClientImpl;
import com.laserfiche.repository.api.clients.impl.model.Entry;
import com.laserfiche.repository.api.clients.impl.model.EntryType;
import com.laserfiche.repository.api.clients.impl.model.ODataValueContextOfIListOfEntry;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.function.Consumer;

public class RemoteEntry extends Entry {

    private final String servicePrincipalKey = "Y-d41brUxxmC-Wlhcdnh";
    private final String accessKeyBase64 = "ewoJImN1c3RvbWVySWQiOiAiMTQwMTM1OTIzOCIsCgkiY2xpZW50SWQiOiAiOTQ4MjMyMTAtMTg4MS00ZjliLThkOTEtYmViMThlMzIyMDY5IiwKCSJkb21haW4iOiAibGFzZXJmaWNoZS5jYSIsCgkiandrIjogewoJCSJrdHkiOiAiRUMiLAoJCSJjcnYiOiAiUC0yNTYiLAoJCSJ1c2UiOiAic2lnIiwKCQkia2lkIjogIkRWOU9qcGtleDZaUHpRM0VJMUV6Q2FLZDVNS3lNa1dYcmU2WmV6WU4zMEUiLAoJCSJ4IjogIkxDSlpEOG1NR2paTm8zWk05ZnFKNk9tQmpHT2hNQjFmajBEOG5OUk9sZGciLAoJCSJ5IjogIkd5Y2JROWd1TlItWXlTYnZfQlRGclhUZnBvRWFlb2RtNFJ5ckNub3M2amciLAoJCSJkIjogIlNtekpWUFBXWTIxZDZVU0t2c1RsTVdXRVhURkhhSVlFdFpTZ3h4RER4aWciLAoJCSJpYXQiOiAxNjc3Mjk3NTk2Cgl9Cn0=";
    private final String repoId;
    private final int entryId;
    private final File file;
    private final String location;
    
  
public int getEntryId() {
        return entryId;
    }

    public RemoteEntry(String repoId, int entryId) {
        this.repoId = repoId;
        this.entryId = Math.abs(entryId);
        this.location = "remote";
        this.file = downloadEntry();
        this.setName(file.getName());
        this.setEntryType(file.isDirectory() ? EntryType.FOLDER : EntryType.DOCUMENT);    }

    public String getLocation() {
        return location;
    }

    private File downloadEntry() {
        try (RepositoryApiClient client = createApiClient()) {
            Entry entry = client.getEntriesClient().getEntry(this.repoId, this.entryId, null).join();
            if (entry.getEntryType() == EntryType.DOCUMENT) {
                return downloadDocument(client, entry);
            } else if (entry.getEntryType() == EntryType.FOLDER) {
                return downloadFolder(client, entry);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private RepositoryApiClient createApiClient() {
        try {
            AccessKey accessKey = AccessKey.createFromBase64EncodedAccessKey(accessKeyBase64);
            return RepositoryApiClientImpl.createFromAccessKey(servicePrincipalKey, accessKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private File downloadDocument(RepositoryApiClient client, Entry entry) throws IOException {
        Consumer<InputStream> consumer = inputStream -> {
            try (FileOutputStream outputStream = new FileOutputStream(entry.getName())) {
                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, length);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        client.getEntriesClient().exportDocument(this.repoId, entry.getId(), null, consumer).join();
        return new File(entry.getName());
    }

    private File downloadFolder(RepositoryApiClient client, Entry entry) throws IOException {
        File folder = new File(entry.getName());
        folder.mkdir();

        ODataValueContextOfIListOfEntry result = client.getEntriesClient()
                .getEntryListing(repoId, entryId, true, null, null, null, null, null, "name", null, null, null).join();

        List<Entry> entries = result.getValue();

        for (Entry childEntry : entries) {
            if (childEntry.getEntryType() == EntryType.FOLDER) {
                File subFolder = new File(folder.getAbsolutePath() + File.separator + childEntry.getName());
                subFolder.mkdir();
                downloadFolder(client, childEntry);
            } else {
                File childFile = downloadDocument(client, childEntry);
                childFile.renameTo(new File(folder.getAbsolutePath() + File.separator + childFile.getName()));
            }
        }
        return folder;
    }
}
