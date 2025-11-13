package com.auditoria.model;

import jakarta.persistence.*;

@Entity
public class RespostaChecklist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "auditoria_id")
    private Auditoria auditoria;

    @ManyToOne(optional = false)
    @JoinColumn(name = "item_checklist_id")
    private ItemChecklist itemChecklist;

    @Column(nullable = false)
    private String resultado;

    private String observacoes;

    @Column(nullable = false)
    private Boolean geraNC = false;

    @OneToOne(mappedBy = "respostaChecklist", cascade = CascadeType.ALL)
    private NaoConformidade naoConformidade;

    public RespostaChecklist() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Auditoria getAuditoria() {
        return auditoria;
    }

    public void setAuditoria(Auditoria auditoria) {
        this.auditoria = auditoria;
    }

    public ItemChecklist getItemChecklist() {
        return itemChecklist;
    }

    public void setItemChecklist(ItemChecklist itemChecklist) {
        this.itemChecklist = itemChecklist;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public Boolean getGeraNC() {
        return geraNC;
    }

    public void setGeraNC(Boolean geraNC) {
        this.geraNC = geraNC;
    }

    public NaoConformidade getNaoConformidade() {
        return naoConformidade;
    }

    public void setNaoConformidade(NaoConformidade naoConformidade) {
        this.naoConformidade = naoConformidade;
    }
}