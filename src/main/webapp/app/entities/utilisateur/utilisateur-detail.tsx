import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './utilisateur.reducer';

export const UtilisateurDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const utilisateurEntity = useAppSelector(state => state.utilisateur.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="utilisateurDetailsHeading">
          <Translate contentKey="blogApp.utilisateur.detail.title">Utilisateur</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{utilisateurEntity.id}</dd>
          <dt>
            <span id="idU">
              <Translate contentKey="blogApp.utilisateur.idU">Id U</Translate>
            </span>
          </dt>
          <dd>{utilisateurEntity.idU}</dd>
          <dt>
            <span id="emailU">
              <Translate contentKey="blogApp.utilisateur.emailU">Email U</Translate>
            </span>
          </dt>
          <dd>{utilisateurEntity.emailU}</dd>
          <dt>
            <span id="passwordU">
              <Translate contentKey="blogApp.utilisateur.passwordU">Password U</Translate>
            </span>
          </dt>
          <dd>{utilisateurEntity.passwordU}</dd>
          <dt>
            <span id="nomU">
              <Translate contentKey="blogApp.utilisateur.nomU">Nom U</Translate>
            </span>
          </dt>
          <dd>{utilisateurEntity.nomU}</dd>
          <dt>
            <span id="prenomU">
              <Translate contentKey="blogApp.utilisateur.prenomU">Prenom U</Translate>
            </span>
          </dt>
          <dd>{utilisateurEntity.prenomU}</dd>
          <dt>
            <span id="dateNaissanceU">
              <Translate contentKey="blogApp.utilisateur.dateNaissanceU">Date Naissance U</Translate>
            </span>
          </dt>
          <dd>
            {utilisateurEntity.dateNaissanceU ? (
              <TextFormat value={utilisateurEntity.dateNaissanceU} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="blogApp.utilisateur.userRoles">User Roles</Translate>
          </dt>
          <dd>
            {utilisateurEntity.userRoles
              ? utilisateurEntity.userRoles.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {utilisateurEntity.userRoles && i === utilisateurEntity.userRoles.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>
            <Translate contentKey="blogApp.utilisateur.patients">Patients</Translate>
          </dt>
          <dd>
            {utilisateurEntity.patients
              ? utilisateurEntity.patients.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {utilisateurEntity.patients && i === utilisateurEntity.patients.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/utilisateur" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/utilisateur/${utilisateurEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default UtilisateurDetail;
