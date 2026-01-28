package med.voll.api.domain.medico;

import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.endereco.DadosEndereco;
import med.voll.api.domain.paciente.DadosCadastroPaciente;
import med.voll.api.domain.paciente.Paciente;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // serve para rodar os testes no banco real ao invés do H2
@ActiveProfiles("test")
class MedicoRepositoryTest {

    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("Deveria devolver null quando não houver médico disponível na data")
    void escolherMedicoAleatorioLivreNaDataCenario1() {

        // GIVEN PU ARRENGE - dado que tenho essas informações
        var proximaSegundaAs10Horas = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10,0);

        var medico = cadastrarMedico("medico", "medico@vollmed.com", "123456", Especialidade.CARDIOLOGIA);
        var paciente = cadastrarPaciente("paciente", "paciente@gmail.com", "00000000000");
        cadastrarConsulta(medico, paciente, proximaSegundaAs10Horas);

        // WHEN OR ACT - quando faço essa ação
        var medicoLivre = medicoRepository.
                escolherMedicoAleatorioLivreNaData(Especialidade.CARDIOLOGIA,
                        proximaSegundaAs10Horas);

        // THEN OR ASSERT - então espero esse resultado
        assertThat(medicoLivre).isNull();
    }

    @Test
    @DisplayName("Deveria devolver médico quando estiver disponível na data")
    void escolherMedicoAleatorioLivreNaDataCenario2() {

        var proximaSegundaAs10Horas = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10,0);

        var medico = cadastrarMedico("medico", "medico@vollmed.com", "123456", Especialidade.CARDIOLOGIA);

        var medicoLivre = medicoRepository.
                escolherMedicoAleatorioLivreNaData(Especialidade.CARDIOLOGIA,
                        proximaSegundaAs10Horas);
        assertThat(medicoLivre).isEqualTo(medico);
    }


    // ------ MÉTODOS AUXILIARES ------
    private void cadastrarConsulta(Medico medico, Paciente paciente, LocalDateTime data) {
        em.persist(new Consulta(null, medico, paciente, data, null));
    }

    private Medico cadastrarMedico(String nome, String email, String crm, Especialidade especialidade) {
        var medico = new Medico(dadosMedico(nome, email, crm, especialidade));
        em.persist(medico);
        return medico;
    }

    private Paciente cadastrarPaciente(String nome, String email, String cpf) {
        var paciente = new Paciente(dadosPaciente(nome, email, cpf));
        em.persist(paciente);
        return paciente;
    }

    private DadosCadastroMedico dadosMedico(String nome, String email, String crm, Especialidade especialidade) {
        return new DadosCadastroMedico(
                nome,
                email,
                "61999999999",
                crm,
                especialidade,
                dadosEndereco()
        );
    }

    private DadosCadastroPaciente dadosPaciente(String nome, String email, String cpf) {
        return new DadosCadastroPaciente(
                nome,
                email,
                "61999999999",
                cpf,
                dadosEndereco()
        );
    }

    private DadosEndereco dadosEndereco() {
        return new DadosEndereco(
                "rua xpto",
                "bairro",
                "00000000",
                "Brasilia",
                "DF",
                null,
                null
        );
    }
}