import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPatient } from 'app/shared/model/patient.model';
import { getEntities } from './patient.reducer';

export const Patient = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const patientList = useAppSelector(state => state.patient.entities);
  const loading = useAppSelector(state => state.patient.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="patient-heading" data-cy="PatientHeading">
        <Translate contentKey="blogApp.patient.home.title">Patients</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="blogApp.patient.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/patient/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="blogApp.patient.home.createLabel">Create new Patient</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {patientList && patientList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="blogApp.patient.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="blogApp.patient.idP">Id P</Translate>
                </th>
                <th>
                  <Translate contentKey="blogApp.patient.nomP">Nom P</Translate>
                </th>
                <th>
                  <Translate contentKey="blogApp.patient.prenomP">Prenom P</Translate>
                </th>
                <th>
                  <Translate contentKey="blogApp.patient.dateNaissanceP">Date Naissance P</Translate>
                </th>
                <th>
                  <Translate contentKey="blogApp.patient.tailleP">Taille P</Translate>
                </th>
                <th>
                  <Translate contentKey="blogApp.patient.sexeP">Sexe P</Translate>
                </th>
                <th>
                  <Translate contentKey="blogApp.patient.dateArrivee">Date Arrivee</Translate>
                </th>
                <th>
                  <Translate contentKey="blogApp.patient.chambres">Chambres</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {patientList.map((patient, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/patient/${patient.id}`} color="link" size="sm">
                      {patient.id}
                    </Button>
                  </td>
                  <td>{patient.idP}</td>
                  <td>{patient.nomP}</td>
                  <td>{patient.prenomP}</td>
                  <td>
                    {patient.dateNaissanceP ? (
                      <TextFormat type="date" value={patient.dateNaissanceP} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{patient.tailleP}</td>
                  <td>
                    <Translate contentKey={`blogApp.Sexe.${patient.sexeP}`} />
                  </td>
                  <td>
                    {patient.dateArrivee ? <TextFormat type="date" value={patient.dateArrivee} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    {patient.chambres
                      ? patient.chambres.map((val, j) => (
                          <span key={j}>
                            <Link to={`/chambre/${val.id}`}>{val.id}</Link>
                            {j === patient.chambres.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/patient/${patient.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/patient/${patient.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/patient/${patient.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="blogApp.patient.home.notFound">No Patients found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Patient;
