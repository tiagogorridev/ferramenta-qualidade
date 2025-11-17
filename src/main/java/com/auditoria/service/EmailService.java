package com.auditoria.service;

import com.auditoria.model.NaoConformidade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public void enviarEmailDeNaoConformidade(NaoConformidade nc) {
        String destinatarioEmail = nc.getEmailResponsavel();
        if (destinatarioEmail == null || destinatarioEmail.isEmpty()) {
            System.err.println("Email não enviado: NC " + nc.getCodigoControle() + " está sem email de responsável.");
            throw new RuntimeException("Não Conformidade está sem email de responsável.");
        }

        String nomeProjeto = nc.getRespostaChecklist().getAuditoria().getNomeProjeto();
        String itemDescricao = nc.getRespostaChecklist().getItemChecklist().getDescricao();

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(destinatarioEmail);
            
            message.setSubject("Comunicação de Não Conformidade - " + nomeProjeto + " (" + nc.getCodigoControle() + ")");
            
            String corpo = "Prezado(a) " + nc.getResponsavel() + ",\n\n" +
                "Durante a auditoria de qualidade realizada no projeto '" + nomeProjeto + "', foi identificada a seguinte não conformidade:\n\n" +
                " • Descrição: " + itemDescricao + "\n" +
                " • Classificação: " + nc.getClassificacao().name().replace("_", " ") + "\n" +
                " • Ação Corretiva (Recomendação): " + nc.getAcaoCorretiva() + "\n" +
                " • Prazo de correção: " + nc.getPrazoResolucao().toString() + "\n\n" +
                "Solicitamos que a correção seja realizada dentro do prazo estipulado.\n\n" +
                "Atenciosamente,\n" +
                "Equipe de Garantia da Qualidade";

            message.setText(corpo);
            mailSender.send(message);
            
        } catch (Exception e) {
            System.err.println("Erro ao enviar email: " + e.getMessage());
            throw new RuntimeException("Falha ao enviar email: " + e.getMessage(), e);
        }
    }
}