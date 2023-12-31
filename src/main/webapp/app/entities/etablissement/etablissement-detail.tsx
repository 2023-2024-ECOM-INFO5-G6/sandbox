import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './etablissement.reducer';

export const EtablissementDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const etablissementEntity = useAppSelector(state => state.etablissement.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="etablissementDetailsHeading">
          <Translate contentKey="blogApp.etablissement.detail.title">Etablissement</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{etablissementEntity.id}</dd>
          <dt>
            <span id="idE">
              <Translate contentKey="blogApp.etablissement.idE">Id E</Translate>
            </span>
          </dt>
          <dd>{etablissementEntity.idE}</dd>
          <dt>
            <span id="nomE">
              <Translate contentKey="blogApp.etablissement.nomE">Nom E</Translate>
            </span>
          </dt>
          <dd>{etablissementEntity.nomE}</dd>
          <dt>
            <span id="adresseE">
              <Translate contentKey="blogApp.etablissement.adresseE">Adresse E</Translate>
            </span>
          </dt>
          <dd>{etablissementEntity.adresseE}</dd>
          <dt>
            <Translate contentKey="blogApp.etablissement.utilisateur">Utilisateur</Translate>
          </dt>
          <dd>
            {etablissementEntity.utilisateurs
              ? etablissementEntity.utilisateurs.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {etablissementEntity.utilisateurs && i === etablissementEntity.utilisateurs.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/etablissement" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/etablissement/${etablissementEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default EtablissementDetail;
