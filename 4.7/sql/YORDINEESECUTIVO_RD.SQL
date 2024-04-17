# CLASSRD
    INSERT INTO <SCH>CLASSRD (CLASS_NAME, RELATION_NAME, CORR_CLASS_NAME, OUTER_JOIN, ONLY_SELECT, XMLEXPORTABLE, GRID_DISPLAYABLE, DEF_SETTING_POS, USER_ID, CHECK_DISABLED) VALUES ('YOrdineEsecutivo', 'SpecificaProduzione', 'YAnagrSpecProd', 'Y', 'N', 'N', 'Y', 0, <USER>, 'N');
# CLASSRD_NLS
    INSERT INTO <SCH>CLASSRD_NLS (CLASS_NAME, RELATION_NAME, LANGUAGE, NLS_RELATION_NAME, NLS_COLUMN_TITLE, NLS_LABEL) VALUES ('YOrdineEsecutivo', 'SpecificaProduzione', 'it', 'Specifica di produzione', 'Specifica di produzione', null);
# CLASSRD_ROW
    INSERT INTO <SCH>CLASSRD_ROW (CLASS_NAME, RELATION_NAME, SEQUENCE, ATTRIBUTE_NAME, CD_NAME, CORR_CLASS_NAME, CORR_ATTR_NAME, DEF_SEARCH) VALUES ('YOrdineEsecutivo', 'SpecificaProduzione', 1, 'IdAzienda', null, 'YAnagrSpecProd', 'IdAzienda', 'N');
    INSERT INTO <SCH>CLASSRD_ROW (CLASS_NAME, RELATION_NAME, SEQUENCE, ATTRIBUTE_NAME, CD_NAME, CORR_CLASS_NAME, CORR_ATTR_NAME, DEF_SEARCH) VALUES ('YOrdineEsecutivo', 'SpecificaProduzione', 2, 'IdSpecificaProduzione', null, 'YAnagrSpecProd', 'IdSpecifica', 'N');
    INSERT INTO <SCH>CLASSRD_ROW (CLASS_NAME, RELATION_NAME, SEQUENCE, ATTRIBUTE_NAME, CD_NAME, CORR_CLASS_NAME, CORR_ATTR_NAME, DEF_SEARCH) VALUES ('YOrdineEsecutivo', 'SpecificaProduzione', 3, null, null, 'YAnagrSpecProd', 'TitoloSpecifica', 'Y');
