import {
  entityTableSelector,
  entityDetailsButtonSelector,
  entityDetailsBackButtonSelector,
  entityCreateButtonSelector,
  entityCreateSaveButtonSelector,
  entityCreateCancelButtonSelector,
  entityEditButtonSelector,
  entityDeleteButtonSelector,
  entityConfirmDeleteButtonSelector,
} from '../../support/entity';

describe('Utilisateur e2e test', () => {
  const utilisateurPageUrl = '/utilisateur';
  const utilisateurPageUrlPattern = new RegExp('/utilisateur(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  // const utilisateurSample = {"idU":1691,"emailU":".R@ShTQC.m0mH2.U.zXL1P.G1j.Gwj.Fct","passwordU":"dF*||b9V"};

  let utilisateur;
  // let userRole;
  // let patient;
  // let etablissement;

  beforeEach(() => {
    cy.login(username, password);
  });

  /* Disabled due to incompatibility
  beforeEach(() => {
    // create an instance at the required relationship entity:
    cy.authenticatedRequest({
      method: 'POST',
      url: '/api/user-roles',
      body: {"role":"ADMIN"},
    }).then(({ body }) => {
      userRole = body;
    });
    // create an instance at the required relationship entity:
    cy.authenticatedRequest({
      method: 'POST',
      url: '/api/patients',
      body: {"idP":693,"nomP":"methodology","prenomP":"leverage overriding","dateNaissanceP":"2023-11-11","tailleP":70582,"sexeP":"FEMME","dateArrivee":"2023-11-11"},
    }).then(({ body }) => {
      patient = body;
    });
    // create an instance at the required relationship entity:
    cy.authenticatedRequest({
      method: 'POST',
      url: '/api/etablissements',
      body: {"idE":78282,"nomE":"Sports","adresseE":"Books"},
    }).then(({ body }) => {
      etablissement = body;
    });
  });
   */

  beforeEach(() => {
    cy.intercept('GET', '/api/utilisateurs+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/utilisateurs').as('postEntityRequest');
    cy.intercept('DELETE', '/api/utilisateurs/*').as('deleteEntityRequest');
  });

  /* Disabled due to incompatibility
  beforeEach(() => {
    // Simulate relationships api for better performance and reproducibility.
    cy.intercept('GET', '/api/user-roles', {
      statusCode: 200,
      body: [userRole],
    });

    cy.intercept('GET', '/api/patients', {
      statusCode: 200,
      body: [patient],
    });

    cy.intercept('GET', '/api/etablissements', {
      statusCode: 200,
      body: [etablissement],
    });

  });
   */

  afterEach(() => {
    if (utilisateur) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/utilisateurs/${utilisateur.id}`,
      }).then(() => {
        utilisateur = undefined;
      });
    }
  });

  /* Disabled due to incompatibility
  afterEach(() => {
    if (userRole) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/user-roles/${userRole.id}`,
      }).then(() => {
        userRole = undefined;
      });
    }
    if (patient) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/patients/${patient.id}`,
      }).then(() => {
        patient = undefined;
      });
    }
    if (etablissement) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/etablissements/${etablissement.id}`,
      }).then(() => {
        etablissement = undefined;
      });
    }
  });
   */

  it('Utilisateurs menu should load Utilisateurs page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('utilisateur');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Utilisateur').should('exist');
    cy.url().should('match', utilisateurPageUrlPattern);
  });

  describe('Utilisateur page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(utilisateurPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Utilisateur page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/utilisateur/new$'));
        cy.getEntityCreateUpdateHeading('Utilisateur');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', utilisateurPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      /* Disabled due to incompatibility
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/utilisateurs',
          body: {
            ...utilisateurSample,
            userRoles: userRole,
            patients: patient,
            etablissements: etablissement,
          },
        }).then(({ body }) => {
          utilisateur = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/utilisateurs+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [utilisateur],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(utilisateurPageUrl);

        cy.wait('@entitiesRequestInternal');
      });
       */

      beforeEach(function () {
        cy.visit(utilisateurPageUrl);

        cy.wait('@entitiesRequest').then(({ response }) => {
          if (response.body.length === 0) {
            this.skip();
          }
        });
      });

      it('detail button click should load details Utilisateur page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('utilisateur');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', utilisateurPageUrlPattern);
      });

      it('edit button click should load edit Utilisateur page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Utilisateur');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', utilisateurPageUrlPattern);
      });

      it('edit button click should load edit Utilisateur page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Utilisateur');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', utilisateurPageUrlPattern);
      });

      it.skip('last delete button click should delete instance of Utilisateur', () => {
        cy.intercept('GET', '/api/utilisateurs/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('utilisateur').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', utilisateurPageUrlPattern);

        utilisateur = undefined;
      });
    });
  });

  describe('new Utilisateur page', () => {
    beforeEach(() => {
      cy.visit(`${utilisateurPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Utilisateur');
    });

    it.skip('should create an instance of Utilisateur', () => {
      cy.get(`[data-cy="idU"]`).type('82974').should('have.value', '82974');

      cy.get(`[data-cy="emailU"]`)
        .type('fYYF2@LNv.TYZc4.unxrTd.xOj.7Ky68Gk30hh')
        .should('have.value', 'fYYF2@LNv.TYZc4.unxrTd.xOj.7Ky68Gk30hh');

      cy.get(`[data-cy="passwordU"]`).type('O)&#39;9W&amp;y-OH~').should('have.value', 'O)&#39;9W&amp;y-OH~');

      cy.get(`[data-cy="nomU"]`).type('input niches').should('have.value', 'input niches');

      cy.get(`[data-cy="prenomU"]`).type('Saint-Séverin mindshare').should('have.value', 'Saint-Séverin mindshare');

      cy.get(`[data-cy="dateNaissanceU"]`).type('2023-11-11').blur().should('have.value', '2023-11-11');

      cy.get(`[data-cy="userRoles"]`).select([0]);
      cy.get(`[data-cy="patients"]`).select([0]);
      cy.get(`[data-cy="etablissements"]`).select([0]);

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        utilisateur = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', utilisateurPageUrlPattern);
    });
  });
});
