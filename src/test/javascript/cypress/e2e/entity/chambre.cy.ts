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

describe('Chambre e2e test', () => {
  const chambrePageUrl = '/chambre';
  const chambrePageUrlPattern = new RegExp('/chambre(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  // const chambreSample = {"numC":"Self-enabling"};

  let chambre;
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
      body: {"idP":28406,"nomP":"Plastic","prenomP":"1080p","dateNaissanceP":"2023-11-12","tailleP":6479,"sexeP":"AUTRE","dateArrivee":"2023-11-12"},
    }).then(({ body }) => {
      patient = body;
    });
  });
   */

  beforeEach(() => {
    cy.intercept('GET', '/api/chambres+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/chambres').as('postEntityRequest');
    cy.intercept('DELETE', '/api/chambres/*').as('deleteEntityRequest');
  });

  /* Disabled due to incompatibility
  beforeEach(() => {
    // Simulate relationships api for better performance and reproducibility.
    cy.intercept('GET', '/api/etablissements', {
      statusCode: 200,
      body: [],
    });

    cy.intercept('GET', '/api/patients', {
      statusCode: 200,
      body: [patient],
    });

  });
   */

  afterEach(() => {
    if (chambre) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/chambres/${chambre.id}`,
      }).then(() => {
        chambre = undefined;
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

  it('Chambres menu should load Chambres page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('chambre');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Chambre').should('exist');
    cy.url().should('match', chambrePageUrlPattern);
  });

  describe('Chambre page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(chambrePageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Chambre page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/chambre/new$'));
        cy.getEntityCreateUpdateHeading('Chambre');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', chambrePageUrlPattern);
      });
    });

    describe('with existing value', () => {
      /* Disabled due to incompatibility
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/chambres',
          body: {
            ...chambreSample,
            patients: patient,
          },
        }).then(({ body }) => {
          chambre = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/chambres+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [chambre],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(chambrePageUrl);

        cy.wait('@entitiesRequestInternal');
      });
       */

      beforeEach(function () {
        cy.visit(chambrePageUrl);

        cy.wait('@entitiesRequest').then(({ response }) => {
          if (response.body.length === 0) {
            this.skip();
          }
        });
      });

      it('detail button click should load details Chambre page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('chambre');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', chambrePageUrlPattern);
      });

      it('edit button click should load edit Chambre page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Chambre');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', chambrePageUrlPattern);
      });

      it('edit button click should load edit Chambre page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Chambre');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', chambrePageUrlPattern);
      });

      it.skip('last delete button click should delete instance of Chambre', () => {
        cy.intercept('GET', '/api/chambres/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('chambre').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', chambrePageUrlPattern);

        chambre = undefined;
      });
    });
  });

  describe('new Chambre page', () => {
    beforeEach(() => {
      cy.visit(`${chambrePageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Chambre');
    });

    it.skip('should create an instance of Chambre', () => {
      cy.get(`[data-cy="numC"]`).type('Bacon enable cross-media').should('have.value', 'Bacon enable cross-media');

      cy.get(`[data-cy="patients"]`).select([0]);

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        chambre = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', chambrePageUrlPattern);
    });
  });
});
