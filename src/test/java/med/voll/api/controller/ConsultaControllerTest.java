package med.voll.api.controller;

import med.voll.api.domain.consulta.AgendaDeConsultas;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.consulta.DadosDetalhamentoConsulta;
import med.voll.api.domain.medico.Especialidade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ConsultaControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private JacksonTester<DadosAgendamentoConsulta> dadosAgendamentoConsultaJson;
    @Autowired
    private JacksonTester<DadosDetalhamentoConsulta> dadosDetalhamentoConsultaJson;

    @MockBean
    private AgendaDeConsultas agendaDeConsultas;

    @Test
    @DisplayName("Deveria devolver o código 400 quando os dados de agendamento forem inválidos")
    @WithMockUser // simula um usuário autenticado ou melhor dizendo, ignora a autenticação
    void agendarCenario1() throws Exception {

        // simula uma requisição HTTP para o endpoint /agendar
        var response = mvc.perform(post("/consultas"))
                .andReturn().getResponse();


        // AS DUAS OPÇÕES FUNCIONAM
//        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getStatus()).isEqualTo(400);
    }

    @Test
    @DisplayName("Deveria devolver o código 200 quando os dados forem válidos")
    @WithMockUser // simula um usuário autenticado ou melhor dizendo, ignora a autenticação
    void agendarCenario2() throws Exception {

        var data = LocalDateTime.now().plusHours(1);
        var especialidade = Especialidade.CARDIOLOGIA;
        var dadosDetalhamento = new DadosDetalhamentoConsulta(null, 2L, 5L, data);

        /*  configurando mock do serviço AgendaDeConsultas
         *   no caso, estamos dizendo que quando o método agendar for chamado
         *   com qualquer objeto do tipo DadosAgendamentoConsulta (any), ele deve retornar
         *   um novo objeto DadosDetalhamentoConsulta com os valores especificados (dadosDetalhamento)
         */
        when(agendaDeConsultas.agendar(any(DadosAgendamentoConsulta.class))).thenReturn(dadosDetalhamento);

        // simula uma requisição HTTP para o endpoint /agendar
        var response = mvc.perform(
                post("/consultas")
                        // cabeçalho da requisição
                        .contentType(MediaType.APPLICATION_JSON)
                        // corpo da requisição - body json
                        .content(dadosAgendamentoConsultaJson
                            .write(new DadosAgendamentoConsulta(2L, 5L, data, especialidade))
                            .getJson()
                        )
                )
                .andReturn()
                .getResponse();

        // verifica se o status da resposta é 200 OK
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        var jsonEsperado = dadosDetalhamentoConsultaJson.write(dadosDetalhamento).getJson();

        // verifica se o corpo da resposta é igual ao JSON esperado
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }
}