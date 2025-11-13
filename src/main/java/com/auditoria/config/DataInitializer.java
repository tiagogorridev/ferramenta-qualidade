package com.auditoria.config;

import com.auditoria.model.ItemChecklist;
import com.auditoria.repository.ItemChecklistRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    private final ItemChecklistRepository checklistRepository;

    public DataInitializer(ItemChecklistRepository checklistRepository) {
        this.checklistRepository = checklistRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Checklist para Plano de Capacitação baseado no processo CAP do MPS.BR
        
        criarItem(1, "Identificação de Necessidades", 
            "As necessidades de capacitação foram identificadas?");
        
        criarItem(2, "Identificação de Necessidades", 
            "As necessidades foram identificadas considerando o planejamento estratégico?");
        
        criarItem(3, "Identificação de Necessidades", 
            "As necessidades foram identificadas considerando objetivos de curto prazo?");
        
        criarItem(4, "Identificação de Necessidades", 
            "As necessidades estão relacionadas aos processos organizacionais?");
        
        criarItem(5, "Identificação de Necessidades", 
            "As necessidades estão relacionadas aos projetos/serviços?");
        
        criarItem(6, "Planejamento de Capacitação", 
            "Foi definido um plano para atendimento das necessidades de capacitação?");
        
        criarItem(7, "Planejamento de Capacitação", 
            "O plano de capacitação está documentado?");
        
        criarItem(8, "Planejamento de Capacitação", 
            "O plano identifica os treinamentos necessários?");
        
        criarItem(9, "Planejamento de Capacitação", 
            "O plano define cronograma para os treinamentos?");
        
        criarItem(10, "Planejamento de Capacitação", 
            "O plano define responsáveis pelos treinamentos?");
        
        criarItem(11, "Planejamento de Capacitação", 
            "O plano está sendo seguido conforme planejado?");
        
        criarItem(12, "Planejamento de Capacitação", 
            "O plano é mantido atualizado?");
        
        criarItem(13, "Execução de Treinamentos", 
            "Os treinamentos identificados como necessários foram realizados?");
        
        criarItem(14, "Execução de Treinamentos", 
            "Os treinamentos foram registrados adequadamente?");
        
        criarItem(15, "Execução de Treinamentos", 
            "Os registros incluem data de realização?");
        
        criarItem(16, "Execução de Treinamentos", 
            "Os registros incluem participantes?");
        
        criarItem(17, "Execução de Treinamentos", 
            "Os registros incluem carga horária?");
        
        criarItem(18, "Execução de Treinamentos", 
            "Os registros incluem conteúdo abordado?");
        
        criarItem(19, "Execução de Treinamentos", 
            "Foi verificada a presença dos colaboradores nos treinamentos?");
        
        criarItem(20, "Avaliação de Efetividade", 
            "A efetividade do programa de treinamentos é avaliada?");
        
        criarItem(21, "Avaliação de Efetividade", 
            "A avaliação é realizada periodicamente?");
        
        criarItem(22, "Avaliação de Efetividade", 
            "Existem critérios definidos para avaliar a efetividade?");
        
        criarItem(23, "Avaliação de Efetividade", 
            "Os resultados das avaliações são documentados?");
        
        criarItem(24, "Avaliação de Efetividade", 
            "Os resultados são comunicados à organização?");
        
        criarItem(25, "Avaliação de Efetividade", 
            "São coletados feedbacks dos participantes?");
        
        criarItem(26, "Desenvolvimento de Recursos", 
            "As habilidades dos instrutores são desenvolvidas?");
        
        criarItem(27, "Desenvolvimento de Recursos", 
            "As habilidades dos instrutores são mantidas atualizadas?");
        
        criarItem(28, "Desenvolvimento de Recursos", 
            "Materiais de apoio para treinamento são desenvolvidos?");
        
        criarItem(29, "Desenvolvimento de Recursos", 
            "Materiais de apoio são mantidos atualizados?");
        
        criarItem(30, "Desenvolvimento de Recursos", 
            "Materiais de apoio estão disponibilizados aos colaboradores?");
        
        criarItem(31, "Infraestrutura", 
            "A infraestrutura necessária para treinamentos está disponível?");
        
        criarItem(32, "Infraestrutura", 
            "A infraestrutura é adequada para os treinamentos planejados?");
        
        criarItem(33, "Infraestrutura", 
            "Recursos tecnológicos necessários estão disponíveis?");
        
        criarItem(34, "Análise de Registros", 
            "Os registros de treinamentos são analisados?");
        
        criarItem(35, "Análise de Registros", 
            "A análise considera dados históricos?");
        
        criarItem(36, "Análise de Registros", 
            "A partir da análise, melhorias são identificadas?");
        
        criarItem(37, "Documentação", 
            "O Plano de Capacitação foi criado conforme template estabelecido?");
        
        criarItem(38, "Documentação", 
            "O Plano de Capacitação foi aprovado?");
        
        criarItem(39, "Documentação", 
            "Existe controle de versão do Plano de Capacitação?");
        
        criarItem(40, "Documentação", 
            "O plano está armazenado no repositório adequado?");
        
        System.out.println("✓ Checklist de Plano de Capacitação inicializado com 40 itens!");
    }
    
    private void criarItem(int numero, String categoria, String descricao) {
        ItemChecklist item = new ItemChecklist();
        item.setNumero(numero);
        item.setCategoria(categoria);
        item.setDescricao(descricao);
        item.setAtivo(true);
        checklistRepository.save(item);
    }
}