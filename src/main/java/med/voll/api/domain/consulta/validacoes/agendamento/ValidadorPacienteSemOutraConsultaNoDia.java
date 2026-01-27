package med.voll.api.domain.consulta.validacoes.agendamento;

import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.infra.exception.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorPacienteSemOutraConsultaNoDia implements ValidadorAgendamentoDeConsulta {

    @Autowired
    private ConsultaRepository consultaRepository;

    public void validar(DadosAgendamentoConsulta dados) {
        var primeiroHorario = dados.data().withHour(7);
        var segundoHorario = dados.data().withHour(18);

        var pacientePossuiOutraConsultaNoDia = consultaRepository
                .existsByPacienteIdAndDataBetween(dados.idPaciente(), primeiroHorario, segundoHorario);

        if (pacientePossuiOutraConsultaNoDia){
            throw new ValidacaoException("Paciente ja possui outra consulta agendada nesse dia.");
        }
    }

}
