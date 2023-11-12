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

describe('Repas e2e test', () => {
  const repasPageUrl = '/repas';
  const repasPageUrlPattern = new RegExp('/repas(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  // const repasSample = {"idR":15408};

  let repas;
  // let patient;

  beforeEach(() => {
    cy.login(username, password);
  });

  /* Disabled due to incompatibility
  beforeEach(() => {
    // create an instance at the required relationship entity:
    cy.authenticatedRequest({
      method: 'POST',
      url: '/api/patients',
      body: {"idP":65950,"nomP":"withdrawal invoice","prenomP":"b Languedoc-Roussillon","dateNaissanceP":"2023-11-12","tailleP":78329,"sexeP":"AUTRE","dateArrivee":"2023-11-12"},
    }).then(({ body }) => {
      patient = body;
    });
  });
   */

  beforeEach(() => {
    cy.intercept('GET', '/api/repas+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/repas').as('postEntityRequest');
    cy.intercept('DELETE', '/api/repas/*').as('deleteEntityRequest');
  });

  /* Disabled due to incompatibility
  beforeEach(() => {
    // Simulate relationships api for better performance and reproducibility.
    cy.intercept('GET', '/api/patients', {
      statusCode: 200,
      body: [patient],
    });

  });
   */

  afterEach(() => {
    if (repas) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/repas/${repas.id}`,
      }).then(() => {
        repas = undefined;
      });
    }
  });

  /* Disabled due to incompatibility
  afterEach(() => {
    if (patient) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/patients/${patient.id}`,
      }).then(() => {
        patient = undefined;
      });
    }
  });
   */

  it('Repas menu should load Repas page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('repas');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Repas').should('exist');
    cy.url().should('match', repasPageUrlPattern);
  });

  describe('Repas page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(repasPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Repas page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/repas/new$'));
        cy.getEntityCreateUpdateHeading('Repas');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', repasPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      /* Disabled due to incompatibility
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/repas',
          body: {
            ...repasSample,
            patient: patient,
          },
        }).then(({ body }) => {
          repas = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/repas+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [repas],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(repasPageUrl);

        cy.wait('@entitiesRequestInternal');
      });
       */

      beforeEach(function () {
        cy.visit(repasPageUrl);

        cy.wait('@entitiesRequest').then(({ response }) => {
          if (response.body.length === 0) {
            this.skip();
          }
        });
      });

      it('detail button click should load details Repas page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('repas');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', repasPageUrlPattern);
      });

      it('edit button click should load edit Repas page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Repas');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', repasPageUrlPattern);
      });

      it('edit button click should load edit Repas page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Repas');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', repasPageUrlPattern);
      });

      it.skip('last delete button click should delete instance of Repas', () => {
        cy.intercept('GET', '/api/repas/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('repas').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', repasPageUrlPattern);

        repas = undefined;
      });
    });
  });

  describe('new Repas page', () => {
    beforeEach(() => {
      cy.visit(`${repasPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Repas');
    });

    it.skip('should create an instance of Repas', () => {
      cy.get(`[data-cy="idR"]`).type('33100').should('have.value', '33100');

      cy.get(`[data-cy="dateR"]`).type('2023-11-12').blur().should('have.value', '2023-11-12');

      cy.get(`[data-cy="heureR"]`).type('2023-11-12T14:24').blur().should('have.value', '2023-11-12T14:24');

      cy.get(`[data-cy="epa"]`).type('1271').should('have.value', '1271');

      cy.get(`[data-cy="patient"]`).select(1);

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        repas = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', repasPageUrlPattern);
    });
  });
});
