package br.unitins.tp2.util;

import java.io.IOException;
import java.io.InputStream;

import br.unitins.tp2.service.PlanoFileServiceImpl;
import io.quarkus.arc.profile.IfBuildProfile;
import io.quarkus.runtime.StartupEvent;
import jakarta.annotation.Priority;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

@ApplicationScoped
@IfBuildProfile("dev")
public class PlanoImageSeeder {

    private static final Logger LOG = Logger.getLogger(PlanoImageSeeder.class);

    @Inject
    PlanoFileServiceImpl fileService;

    void onStart(@Observes @Priority(3000) StartupEvent event) {
        salvarImagem(1L, "images/PlanoBasico.png");
        salvarImagem(2L, "images/PlanoBasico.png");
        salvarImagem(3L, "images/PlanoBasico.png");
        salvarImagem(4L, "images/PlanoBasico.png");
        salvarImagem(5L, "images/PlanoEnterprise.png");
    }

    private void salvarImagem(Long planoId, String resourcePath) {
        try (InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream(resourcePath)) {
            if (input == null) {
                LOG.warnf("[PlanoImageSeeder] imagem não encontrada em src/main/resources/%s", resourcePath);
                return;
            }

            byte[] bytes = input.readAllBytes();
            String nomeArquivo = resourcePath.substring(resourcePath.lastIndexOf('/') + 1);
            fileService.salvarLocal(planoId, nomeArquivo, "image/png", bytes);
            LOG.infof("[PlanoImageSeeder] imagem %s associada ao plano %d", nomeArquivo, planoId);
        } catch (IOException e) {
            LOG.warnf(e, "[PlanoImageSeeder] falha ao ler imagem %s", resourcePath);
        } catch (Exception e) {
            LOG.warnf(e, "[PlanoImageSeeder] falha ao enviar imagem %s para o plano %d", resourcePath, planoId);
        }
    }
}
